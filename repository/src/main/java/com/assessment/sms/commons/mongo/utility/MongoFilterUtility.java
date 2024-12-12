package com.assessment.sms.commons.mongo.utility;

import com.assessment.sms.commons.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.assessment.sms.commons.model.FilterFieldType.withOutValueType;
import static com.assessment.sms.commons.mongo.utility.MongoFieldConstants.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class MongoFilterUtility {

    private MongoFilterUtility() {
    }

    public static void addFilterSortingPaginationOperations(FilterRequest filterRequest, List<AggregationOperation> operations,
            Criteria criteria, Map<String, FilterField> filterMap, List<String> searchFieldList) {
        operations.add(Aggregation.match(criteria));
        Criteria filterCriteria = new Criteria();
        filter(filterRequest.getSearchText(), filterRequest.getFilter(), filterCriteria, filterMap, searchFieldList);
        operations.add(Aggregation.match(filterCriteria));
        sortDataWithExtraFieldModifiedOn(filterRequest.getSort(), operations);
        pagination(filterRequest, operations);
    }

    public static Criteria nonDeletedCriteria() {
        return Criteria.where(DELETED).is(FALSE);
    }

    public static Criteria deletedCriteria() {
        return Criteria.where(DELETED).is(TRUE);
    }

    public static Criteria unArchivedNonDeletedCriteria() {
        return nonDeletedCriteria().and(ARCHIVED).is(FALSE);
    }

    public static Criteria activeUnArchivedNonDeletedCriteria() {
        return unArchivedNonDeletedCriteria().and(ACTIVE).is(Boolean.TRUE);
    }

    public static void filter(String searchText, List<FilterModel> filterList, Criteria criteria,
            Map<String, FilterField> filterMap, List<String> searchFieldList) {
        getSearchCriteria(searchText, criteria, searchFieldList);

        if (ObjectUtils.isEmpty(filterList)) {
            return;
        }
        for (FilterModel filter : filterList) {
            filter(filter, criteria, filterMap);
        }
    }

    private static void filter(FilterModel filterReq, Criteria criteria, Map<String, FilterField> filterMap) {
        String key = filterReq.getKey();
        FilterField filterField = filterMap.get(key);
        if ((filterField == null) || (!withOutValueType().contains(filterField.getType()) && (filterReq.getValue() == null))) {
            return;
        }

        switch (filterField.getType()) {
            case EXACT:
                criteria.and(filterField.getDbKey()).is(filterReq.getValue());
                break;
            case REGEX:
                regexCriteria(filterReq, criteria, filterField);
                break;
            case LIST:
                listCriteria(filterReq, criteria, filterField);
                break;
            case ID_LIST:
                idListCriteria(filterReq, criteria, filterField);
                break;
            case DATE_RANGE:
                if (filterReq.getStartDate() != null && filterReq.getEndDate() != null) {
                    criteria.and(filterField.getDbKey()).gte(filterReq.getStartDate()).lte(filterReq.getEndDate());
                }
                break;
            case NUMBER:
                if (filterReq.getValue() instanceof Number) {
                    criteria.and(filterField.getDbKey()).is(filterReq.getValue());
                }
                break;
            case EXISTS:
                criteria.and(filterField.getDbKey()).exists(true);
                break;

            case NOT_EXISTS:
                criteria.and(filterField.getDbKey()).exists(false);
                break;
        }

    }

    private static void idListCriteria(FilterModel filterReq, Criteria criteria, FilterField filterField) {
        if (filterReq.getValue() instanceof List) {
            List<ObjectId> objectIdList = ((List<?>) filterReq.getValue()).stream().map(o -> new ObjectId((String) o))
                    .collect(Collectors.toList());
            criteria.and(filterField.getDbKey()).in(objectIdList);
        } else if (filterReq.getValue() instanceof String) {
            criteria.and(filterField.getDbKey()).is(new ObjectId((String) filterReq.getValue()));
        }
    }

    private static void listCriteria(FilterModel filterReq, Criteria criteria, FilterField filterField) {
        if (filterReq.getValue() instanceof List) {
            criteria.and(filterField.getDbKey()).in(((List<?>) filterReq.getValue()).toArray());
        } else {
            criteria.and(filterField.getDbKey()).is(filterReq.getValue());
        }
    }

    private static void regexCriteria(FilterModel filterReq, Criteria criteria, FilterField filterField) {
        if (filterReq.getValue() instanceof String) {
            criteria.and(filterField.getDbKey()).regex(Pattern.quote((String) filterReq.getValue()), "i");
        } else {
            criteria.and(filterField.getDbKey()).is(filterReq.getValue());
        }
    }

    public static boolean filterKeysExists(List<FilterModel> filterList, Collection<?> filterLookUpKeys) {
        if (ObjectUtils.isEmpty(filterList) || ObjectUtils.isEmpty(filterLookUpKeys)) {
            return false;
        }
        List<String> requestKeys = filterList.stream().map(FilterModel::getKey).collect(Collectors.toList());
        return !Collections.disjoint(requestKeys, filterLookUpKeys);
    }

    public static boolean sortingKeysExists(List<SortModel> sortList, Collection<?> sortingLookUpKeys) {
        if (ObjectUtils.isEmpty(sortList) || ObjectUtils.isEmpty(sortingLookUpKeys)) {
            return false;
        }
        List<String> requestKeys = sortList.stream().map(SortModel::getProperty).collect(Collectors.toList());
        return !Collections.disjoint(requestKeys, sortingLookUpKeys);
    }

    public static void sortDataWithExtraFieldModifiedOn(List<SortModel> sort, List<AggregationOperation> aggregationOperations) {
        if (!ObjectUtils.isEmpty(sort) && sort.stream().noneMatch(o -> o.getProperty().equals(MODIFIED_ON))) {
            sort.add(new SortModel(MODIFIED_ON, SortDirection.DESC));
        }
        sortData(sort, aggregationOperations);
    }

    public static void sortData(List<SortModel> sort, List<AggregationOperation> aggregationOperations) {
        SortOperation sortOperation;
        if (ObjectUtils.isEmpty(sort)) {
            sortOperation = Aggregation.sort(DESC, MODIFIED_ON).and(DESC, ID);
        } else {
            SortModel sortPojo = sort.remove(0);
            Sort.Direction direction = sortPojo.getDirection() == SortDirection.ASC ? ASC : DESC;
            sortOperation = Aggregation.sort(direction, sortPojo.getProperty());

            if (!ObjectUtils.isEmpty(sort)) {
                for (SortModel sortDto : sort) {
                    Sort.Direction dir = sortDto.getDirection() == SortDirection.ASC ? ASC : DESC;
                    sortOperation = sortOperation.and(dir, sortDto.getProperty());
                }
            }
        }
        aggregationOperations.add(sortOperation);
    }

    public static void sortNative(List<SortModel> sort, Collection<AggregationOperation> operations) {
        if (!ObjectUtils.isEmpty(sort)) {
            Document document = new Document();
            sort.forEach(d -> document.append(d.getProperty(), d.getDirection() == SortDirection.ASC ? 1 : -1));
            operations.add(new AggregationOperationImpl(new Document("$sort", document)));
        }
    }

    public static void getSearchCriteria(String searchText, Criteria criteria, List<String> searchFieldList) {
        if (!StringUtils.hasLength(searchText) || ObjectUtils.isEmpty(searchFieldList)) {
            return;
        }
        List<Criteria> orCriteria = new ArrayList<>(searchFieldList.size());
        for (String searchField : searchFieldList) {
            orCriteria.add(Criteria.where(searchField).regex(Pattern.quote(searchText), "i"));
        }
        criteria.orOperator(orCriteria.toArray(new Criteria[0]));
    }

    public static void pagination(PaginationRequest paginationRequest, List<AggregationOperation> aggregationOperations) {
        if (paginationRequest == null) {
            aggregationOperations.add(Aggregation.skip(0L));
            return;
        }
        Pagination pagination = Pagination.get(paginationRequest);
        pagination(pagination, aggregationOperations);
    }

    public static void pagination(Pagination pagination, List<AggregationOperation> aggregationOperations) {
        if (pagination == null) {
            aggregationOperations.add(Aggregation.skip(0L));
            return;
        }
        if (pagination.isPaginationRequired()) {
            aggregationOperations.add(Aggregation.skip((long) pagination.getStart()));
            aggregationOperations.add(Aggregation.limit(pagination.getEnd()));
        }
    }

    public static void addPagination(PaginationRequest paginationRequest, Query query) {
        if (paginationRequest == null) {
            return;
        }
        Pagination pagination = Pagination.get(paginationRequest);
        if (pagination.isPaginationRequired()) {
            query.skip(pagination.getStart());
            query.limit(pagination.getEnd());
        }
    }

    public static void sort(List<SortModel> sort, Query query) {
        List<Sort.Order> orders;
        if (!ObjectUtils.isEmpty(sort)) {
            orders = sort.stream().map(s -> new Sort.Order(
                    s.getDirection() == SortDirection.ASC ? ASC : DESC,
                    s.getProperty())).collect(Collectors.toList());
        } else {
            orders = Collections.singletonList(new Sort.Order(DESC, MODIFIED_ON));
        }
        query.with(Sort.by(orders));
    }
}

