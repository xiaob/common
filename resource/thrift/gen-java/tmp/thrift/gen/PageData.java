/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package tmp.thrift.gen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageData implements org.apache.thrift.TBase<PageData, PageData._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PageData");

  private static final org.apache.thrift.protocol.TField TOTAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalCount", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAGE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("pageSize", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField CURRENT_PAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("currentPage", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField DATA_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dataList", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PageDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PageDataTupleSchemeFactory());
  }

  public int totalCount; // required
  public int pageSize; // required
  public int currentPage; // required
  public List<WorldPlayer> dataList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_COUNT((short)1, "totalCount"),
    PAGE_SIZE((short)2, "pageSize"),
    CURRENT_PAGE((short)3, "currentPage"),
    DATA_LIST((short)4, "dataList");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TOTAL_COUNT
          return TOTAL_COUNT;
        case 2: // PAGE_SIZE
          return PAGE_SIZE;
        case 3: // CURRENT_PAGE
          return CURRENT_PAGE;
        case 4: // DATA_LIST
          return DATA_LIST;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOTALCOUNT_ISSET_ID = 0;
  private static final int __PAGESIZE_ISSET_ID = 1;
  private static final int __CURRENTPAGE_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("totalCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAGE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("pageSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CURRENT_PAGE, new org.apache.thrift.meta_data.FieldMetaData("currentPage", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DATA_LIST, new org.apache.thrift.meta_data.FieldMetaData("dataList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, WorldPlayer.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PageData.class, metaDataMap);
  }

  public PageData() {
  }

  public PageData(
    int totalCount,
    int pageSize,
    int currentPage,
    List<WorldPlayer> dataList)
  {
    this();
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    this.currentPage = currentPage;
    setCurrentPageIsSet(true);
    this.dataList = dataList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PageData(PageData other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.totalCount = other.totalCount;
    this.pageSize = other.pageSize;
    this.currentPage = other.currentPage;
    if (other.isSetDataList()) {
      List<WorldPlayer> __this__dataList = new ArrayList<WorldPlayer>();
      for (WorldPlayer other_element : other.dataList) {
        __this__dataList.add(new WorldPlayer(other_element));
      }
      this.dataList = __this__dataList;
    }
  }

  public PageData deepCopy() {
    return new PageData(this);
  }

  @Override
  public void clear() {
    setTotalCountIsSet(false);
    this.totalCount = 0;
    setPageSizeIsSet(false);
    this.pageSize = 0;
    setCurrentPageIsSet(false);
    this.currentPage = 0;
    this.dataList = null;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public PageData setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    return this;
  }

  public void unsetTotalCount() {
    __isset_bit_vector.clear(__TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return __isset_bit_vector.get(__TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bit_vector.set(__TOTALCOUNT_ISSET_ID, value);
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public PageData setPageSize(int pageSize) {
    this.pageSize = pageSize;
    setPageSizeIsSet(true);
    return this;
  }

  public void unsetPageSize() {
    __isset_bit_vector.clear(__PAGESIZE_ISSET_ID);
  }

  /** Returns true if field pageSize is set (has been assigned a value) and false otherwise */
  public boolean isSetPageSize() {
    return __isset_bit_vector.get(__PAGESIZE_ISSET_ID);
  }

  public void setPageSizeIsSet(boolean value) {
    __isset_bit_vector.set(__PAGESIZE_ISSET_ID, value);
  }

  public int getCurrentPage() {
    return this.currentPage;
  }

  public PageData setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
    setCurrentPageIsSet(true);
    return this;
  }

  public void unsetCurrentPage() {
    __isset_bit_vector.clear(__CURRENTPAGE_ISSET_ID);
  }

  /** Returns true if field currentPage is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrentPage() {
    return __isset_bit_vector.get(__CURRENTPAGE_ISSET_ID);
  }

  public void setCurrentPageIsSet(boolean value) {
    __isset_bit_vector.set(__CURRENTPAGE_ISSET_ID, value);
  }

  public int getDataListSize() {
    return (this.dataList == null) ? 0 : this.dataList.size();
  }

  public java.util.Iterator<WorldPlayer> getDataListIterator() {
    return (this.dataList == null) ? null : this.dataList.iterator();
  }

  public void addToDataList(WorldPlayer elem) {
    if (this.dataList == null) {
      this.dataList = new ArrayList<WorldPlayer>();
    }
    this.dataList.add(elem);
  }

  public List<WorldPlayer> getDataList() {
    return this.dataList;
  }

  public PageData setDataList(List<WorldPlayer> dataList) {
    this.dataList = dataList;
    return this;
  }

  public void unsetDataList() {
    this.dataList = null;
  }

  /** Returns true if field dataList is set (has been assigned a value) and false otherwise */
  public boolean isSetDataList() {
    return this.dataList != null;
  }

  public void setDataListIsSet(boolean value) {
    if (!value) {
      this.dataList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;

    case PAGE_SIZE:
      if (value == null) {
        unsetPageSize();
      } else {
        setPageSize((Integer)value);
      }
      break;

    case CURRENT_PAGE:
      if (value == null) {
        unsetCurrentPage();
      } else {
        setCurrentPage((Integer)value);
      }
      break;

    case DATA_LIST:
      if (value == null) {
        unsetDataList();
      } else {
        setDataList((List<WorldPlayer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_COUNT:
      return Integer.valueOf(getTotalCount());

    case PAGE_SIZE:
      return Integer.valueOf(getPageSize());

    case CURRENT_PAGE:
      return Integer.valueOf(getCurrentPage());

    case DATA_LIST:
      return getDataList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_COUNT:
      return isSetTotalCount();
    case PAGE_SIZE:
      return isSetPageSize();
    case CURRENT_PAGE:
      return isSetCurrentPage();
    case DATA_LIST:
      return isSetDataList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PageData)
      return this.equals((PageData)that);
    return false;
  }

  public boolean equals(PageData that) {
    if (that == null)
      return false;

    boolean this_present_totalCount = true;
    boolean that_present_totalCount = true;
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }

    boolean this_present_pageSize = true;
    boolean that_present_pageSize = true;
    if (this_present_pageSize || that_present_pageSize) {
      if (!(this_present_pageSize && that_present_pageSize))
        return false;
      if (this.pageSize != that.pageSize)
        return false;
    }

    boolean this_present_currentPage = true;
    boolean that_present_currentPage = true;
    if (this_present_currentPage || that_present_currentPage) {
      if (!(this_present_currentPage && that_present_currentPage))
        return false;
      if (this.currentPage != that.currentPage)
        return false;
    }

    boolean this_present_dataList = true && this.isSetDataList();
    boolean that_present_dataList = true && that.isSetDataList();
    if (this_present_dataList || that_present_dataList) {
      if (!(this_present_dataList && that_present_dataList))
        return false;
      if (!this.dataList.equals(that.dataList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(PageData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PageData typedOther = (PageData)other;

    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(typedOther.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalCount, typedOther.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPageSize()).compareTo(typedOther.isSetPageSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPageSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pageSize, typedOther.pageSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrentPage()).compareTo(typedOther.isSetCurrentPage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrentPage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currentPage, typedOther.currentPage);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDataList()).compareTo(typedOther.isSetDataList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataList, typedOther.dataList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PageData(");
    boolean first = true;

    sb.append("totalCount:");
    sb.append(this.totalCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("pageSize:");
    sb.append(this.pageSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("currentPage:");
    sb.append(this.currentPage);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dataList:");
    if (this.dataList == null) {
      sb.append("null");
    } else {
      sb.append(this.dataList);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PageDataStandardSchemeFactory implements SchemeFactory {
    public PageDataStandardScheme getScheme() {
      return new PageDataStandardScheme();
    }
  }

  private static class PageDataStandardScheme extends StandardScheme<PageData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PageData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalCount = iprot.readI32();
              struct.setTotalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAGE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pageSize = iprot.readI32();
              struct.setPageSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CURRENT_PAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.currentPage = iprot.readI32();
              struct.setCurrentPageIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DATA_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.dataList = new ArrayList<WorldPlayer>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  WorldPlayer _elem2; // required
                  _elem2 = new WorldPlayer();
                  _elem2.read(iprot);
                  struct.dataList.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setDataListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PageData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
      oprot.writeI32(struct.totalCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PAGE_SIZE_FIELD_DESC);
      oprot.writeI32(struct.pageSize);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CURRENT_PAGE_FIELD_DESC);
      oprot.writeI32(struct.currentPage);
      oprot.writeFieldEnd();
      if (struct.dataList != null) {
        oprot.writeFieldBegin(DATA_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.dataList.size()));
          for (WorldPlayer _iter3 : struct.dataList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PageDataTupleSchemeFactory implements SchemeFactory {
    public PageDataTupleScheme getScheme() {
      return new PageDataTupleScheme();
    }
  }

  private static class PageDataTupleScheme extends TupleScheme<PageData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PageData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalCount()) {
        optionals.set(0);
      }
      if (struct.isSetPageSize()) {
        optionals.set(1);
      }
      if (struct.isSetCurrentPage()) {
        optionals.set(2);
      }
      if (struct.isSetDataList()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTotalCount()) {
        oprot.writeI32(struct.totalCount);
      }
      if (struct.isSetPageSize()) {
        oprot.writeI32(struct.pageSize);
      }
      if (struct.isSetCurrentPage()) {
        oprot.writeI32(struct.currentPage);
      }
      if (struct.isSetDataList()) {
        {
          oprot.writeI32(struct.dataList.size());
          for (WorldPlayer _iter4 : struct.dataList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PageData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.totalCount = iprot.readI32();
        struct.setTotalCountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pageSize = iprot.readI32();
        struct.setPageSizeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.currentPage = iprot.readI32();
        struct.setCurrentPageIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.dataList = new ArrayList<WorldPlayer>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            WorldPlayer _elem7; // required
            _elem7 = new WorldPlayer();
            _elem7.read(iprot);
            struct.dataList.add(_elem7);
          }
        }
        struct.setDataListIsSet(true);
      }
    }
  }

}
