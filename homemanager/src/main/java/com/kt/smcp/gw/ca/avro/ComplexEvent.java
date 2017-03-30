/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.kt.smcp.gw.ca.avro;

import java.io.Serializable;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class ComplexEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord, Serializable {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ComplexEvent\",\"namespace\":\"com.kt.smcp.gw.ca.avro\",\"fields\":[{\"name\":\"svcTgtSeq\",\"type\":\"long\"},{\"name\":\"evtOutbId\",\"type\":[{\"type\":\"string\",\"avro.java.string\":\"String\"},\"null\"]},{\"name\":\"evtId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"items\",\"type\":{\"type\":\"map\",\"values\":[\"int\",\"long\",\"float\",\"double\",{\"type\":\"string\",\"avro.java.string\":\"String\"},\"boolean\",\"bytes\"],\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public long svcTgtSeq;
  @Deprecated public java.lang.String evtOutbId;
  @Deprecated public java.lang.String evtId;
  @Deprecated public java.util.Map<java.lang.String,java.lang.Object> items;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public ComplexEvent() {}

  /**
   * All-args constructor.
   */
  public ComplexEvent(java.lang.Long svcTgtSeq, java.lang.String evtOutbId, java.lang.String evtId, java.util.Map<java.lang.String,java.lang.Object> items) {
    this.svcTgtSeq = svcTgtSeq;
    this.evtOutbId = evtOutbId;
    this.evtId = evtId;
    this.items = items;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return svcTgtSeq;
    case 1: return evtOutbId;
    case 2: return evtId;
    case 3: return items;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: svcTgtSeq = (java.lang.Long)value$; break;
    case 1: evtOutbId = (java.lang.String)value$; break;
    case 2: evtId = (java.lang.String)value$; break;
    case 3: items = (java.util.Map<java.lang.String,java.lang.Object>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'svcTgtSeq' field.
   */
  public java.lang.Long getSvcTgtSeq() {
    return svcTgtSeq;
  }

  /**
   * Sets the value of the 'svcTgtSeq' field.
   * @param value the value to set.
   */
  public void setSvcTgtSeq(java.lang.Long value) {
    this.svcTgtSeq = value;
  }

  /**
   * Gets the value of the 'evtOutbId' field.
   */
  public java.lang.String getEvtOutbId() {
    return evtOutbId;
  }

  /**
   * Sets the value of the 'evtOutbId' field.
   * @param value the value to set.
   */
  public void setEvtOutbId(java.lang.String value) {
    this.evtOutbId = value;
  }

  /**
   * Gets the value of the 'evtId' field.
   */
  public java.lang.String getEvtId() {
    return evtId;
  }

  /**
   * Sets the value of the 'evtId' field.
   * @param value the value to set.
   */
  public void setEvtId(java.lang.String value) {
    this.evtId = value;
  }

  /**
   * Gets the value of the 'items' field.
   */
  public java.util.Map<java.lang.String,java.lang.Object> getItems() {
    return items;
  }

  /**
   * Sets the value of the 'items' field.
   * @param value the value to set.
   */
  public void setItems(java.util.Map<java.lang.String,java.lang.Object> value) {
    this.items = value;
  }

  /** Creates a new ComplexEvent RecordBuilder */
  public static com.kt.smcp.gw.ca.avro.ComplexEvent.Builder newBuilder() {
    return new com.kt.smcp.gw.ca.avro.ComplexEvent.Builder();
  }

  /** Creates a new ComplexEvent RecordBuilder by copying an existing Builder */
  public static com.kt.smcp.gw.ca.avro.ComplexEvent.Builder newBuilder(com.kt.smcp.gw.ca.avro.ComplexEvent.Builder other) {
    return new com.kt.smcp.gw.ca.avro.ComplexEvent.Builder(other);
  }

  /** Creates a new ComplexEvent RecordBuilder by copying an existing ComplexEvent instance */
  public static com.kt.smcp.gw.ca.avro.ComplexEvent.Builder newBuilder(com.kt.smcp.gw.ca.avro.ComplexEvent other) {
    return new com.kt.smcp.gw.ca.avro.ComplexEvent.Builder(other);
  }

  /**
   * RecordBuilder for ComplexEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ComplexEvent>
    implements org.apache.avro.data.RecordBuilder<ComplexEvent> {

    private long svcTgtSeq;
    private java.lang.String evtOutbId;
    private java.lang.String evtId;
    private java.util.Map<java.lang.String,java.lang.Object> items;

    /** Creates a new Builder */
    private Builder() {
      super(com.kt.smcp.gw.ca.avro.ComplexEvent.SCHEMA$);
    }

    /** Creates a Builder by copying an existing Builder */
    private Builder(com.kt.smcp.gw.ca.avro.ComplexEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.svcTgtSeq)) {
        this.svcTgtSeq = data().deepCopy(fields()[0].schema(), other.svcTgtSeq);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.evtOutbId)) {
        this.evtOutbId = data().deepCopy(fields()[1].schema(), other.evtOutbId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.evtId)) {
        this.evtId = data().deepCopy(fields()[2].schema(), other.evtId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.items)) {
        this.items = data().deepCopy(fields()[3].schema(), other.items);
        fieldSetFlags()[3] = true;
      }
    }

    /** Creates a Builder by copying an existing ComplexEvent instance */
    private Builder(com.kt.smcp.gw.ca.avro.ComplexEvent other) {
            super(com.kt.smcp.gw.ca.avro.ComplexEvent.SCHEMA$);
      if (isValidValue(fields()[0], other.svcTgtSeq)) {
        this.svcTgtSeq = data().deepCopy(fields()[0].schema(), other.svcTgtSeq);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.evtOutbId)) {
        this.evtOutbId = data().deepCopy(fields()[1].schema(), other.evtOutbId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.evtId)) {
        this.evtId = data().deepCopy(fields()[2].schema(), other.evtId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.items)) {
        this.items = data().deepCopy(fields()[3].schema(), other.items);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'svcTgtSeq' field */
    public java.lang.Long getSvcTgtSeq() {
      return svcTgtSeq;
    }

    /** Sets the value of the 'svcTgtSeq' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder setSvcTgtSeq(long value) {
      validate(fields()[0], value);
      this.svcTgtSeq = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /** Checks whether the 'svcTgtSeq' field has been set */
    public boolean hasSvcTgtSeq() {
      return fieldSetFlags()[0];
    }

    /** Clears the value of the 'svcTgtSeq' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder clearSvcTgtSeq() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'evtOutbId' field */
    public java.lang.String getEvtOutbId() {
      return evtOutbId;
    }

    /** Sets the value of the 'evtOutbId' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder setEvtOutbId(java.lang.String value) {
      validate(fields()[1], value);
      this.evtOutbId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /** Checks whether the 'evtOutbId' field has been set */
    public boolean hasEvtOutbId() {
      return fieldSetFlags()[1];
    }

    /** Clears the value of the 'evtOutbId' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder clearEvtOutbId() {
      evtOutbId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'evtId' field */
    public java.lang.String getEvtId() {
      return evtId;
    }

    /** Sets the value of the 'evtId' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder setEvtId(java.lang.String value) {
      validate(fields()[2], value);
      this.evtId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /** Checks whether the 'evtId' field has been set */
    public boolean hasEvtId() {
      return fieldSetFlags()[2];
    }

    /** Clears the value of the 'evtId' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder clearEvtId() {
      evtId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'items' field */
    public java.util.Map<java.lang.String,java.lang.Object> getItems() {
      return items;
    }

    /** Sets the value of the 'items' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder setItems(java.util.Map<java.lang.String,java.lang.Object> value) {
      validate(fields()[3], value);
      this.items = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /** Checks whether the 'items' field has been set */
    public boolean hasItems() {
      return fieldSetFlags()[3];
    }

    /** Clears the value of the 'items' field */
    public com.kt.smcp.gw.ca.avro.ComplexEvent.Builder clearItems() {
      items = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public ComplexEvent build() {
      try {
        ComplexEvent record = new ComplexEvent();
        record.svcTgtSeq = fieldSetFlags()[0] ? this.svcTgtSeq : (java.lang.Long) defaultValue(fields()[0]);
        record.evtOutbId = fieldSetFlags()[1] ? this.evtOutbId : (java.lang.String) defaultValue(fields()[1]);
        record.evtId = fieldSetFlags()[2] ? this.evtId : (java.lang.String) defaultValue(fields()[2]);
        record.items = fieldSetFlags()[3] ? this.items : (java.util.Map<java.lang.String,java.lang.Object>) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
