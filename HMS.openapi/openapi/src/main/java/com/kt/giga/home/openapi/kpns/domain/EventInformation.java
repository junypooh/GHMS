/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.kt.giga.home.openapi.kpns.domain;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class EventInformation extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EventInformation\",\"namespace\":\"com.kt.iot.core.integration.avro\",\"fields\":[{\"name\":\"eventId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventOutbId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventGrade\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"svcTgtSeq\",\"type\":\"long\"},{\"name\":\"spotDevSeq\",\"type\":\"long\"},{\"name\":\"eventItems\",\"type\":{\"type\":\"map\",\"values\":[\"int\",\"long\",\"float\",\"double\",{\"type\":\"string\",\"avro.java.string\":\"String\"},\"boolean\"],\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String eventId;
  @Deprecated public java.lang.String eventOutbId;
  @Deprecated public java.lang.String eventName;
  @Deprecated public java.lang.String eventGrade;
  @Deprecated public long svcTgtSeq;
  @Deprecated public long spotDevSeq;
  @Deprecated public java.util.Map<java.lang.String,java.lang.Object> eventItems;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public EventInformation() {}

  /**
   * All-args constructor.
   */
  public EventInformation(java.lang.String eventId, java.lang.String eventOutbId, java.lang.String eventName, java.lang.String eventGrade, java.lang.Long svcTgtSeq, java.lang.Long spotDevSeq, java.util.Map<java.lang.String,java.lang.Object> eventItems) {
    this.eventId = eventId;
    this.eventOutbId = eventOutbId;
    this.eventName = eventName;
    this.eventGrade = eventGrade;
    this.svcTgtSeq = svcTgtSeq;
    this.spotDevSeq = spotDevSeq;
    this.eventItems = eventItems;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return eventId;
    case 1: return eventOutbId;
    case 2: return eventName;
    case 3: return eventGrade;
    case 4: return svcTgtSeq;
    case 5: return spotDevSeq;
    case 6: return eventItems;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: eventId = (java.lang.String)value$; break;
    case 1: eventOutbId = (java.lang.String)value$; break;
    case 2: eventName = (java.lang.String)value$; break;
    case 3: eventGrade = (java.lang.String)value$; break;
    case 4: svcTgtSeq = (java.lang.Long)value$; break;
    case 5: spotDevSeq = (java.lang.Long)value$; break;
    case 6: eventItems = (java.util.Map<java.lang.String,java.lang.Object>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'eventId' field.
   */
  public java.lang.String getEventId() {
    return eventId;
  }

  /**
   * Sets the value of the 'eventId' field.
   * @param value the value to set.
   */
  public void setEventId(java.lang.String value) {
    this.eventId = value;
  }

  /**
   * Gets the value of the 'eventOutbId' field.
   */
  public java.lang.String getEventOutbId() {
    return eventOutbId;
  }

  /**
   * Sets the value of the 'eventOutbId' field.
   * @param value the value to set.
   */
  public void setEventOutbId(java.lang.String value) {
    this.eventOutbId = value;
  }

  /**
   * Gets the value of the 'eventName' field.
   */
  public java.lang.String getEventName() {
    return eventName;
  }

  /**
   * Sets the value of the 'eventName' field.
   * @param value the value to set.
   */
  public void setEventName(java.lang.String value) {
    this.eventName = value;
  }

  /**
   * Gets the value of the 'eventGrade' field.
   */
  public java.lang.String getEventGrade() {
    return eventGrade;
  }

  /**
   * Sets the value of the 'eventGrade' field.
   * @param value the value to set.
   */
  public void setEventGrade(java.lang.String value) {
    this.eventGrade = value;
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
   * Gets the value of the 'spotDevSeq' field.
   */
  public java.lang.Long getSpotDevSeq() {
    return spotDevSeq;
  }

  /**
   * Sets the value of the 'spotDevSeq' field.
   * @param value the value to set.
   */
  public void setSpotDevSeq(java.lang.Long value) {
    this.spotDevSeq = value;
  }

  /**
   * Gets the value of the 'eventItems' field.
   */
  public java.util.Map<java.lang.String,java.lang.Object> getEventItems() {
    return eventItems;
  }

  /**
   * Sets the value of the 'eventItems' field.
   * @param value the value to set.
   */
  public void setEventItems(java.util.Map<java.lang.String,java.lang.Object> value) {
    this.eventItems = value;
  }

  /** Creates a new EventInformation RecordBuilder */
  public static EventInformation.Builder newBuilder() {
    return new EventInformation.Builder();
  }
  
  /** Creates a new EventInformation RecordBuilder by copying an existing Builder */
  public static EventInformation.Builder newBuilder(EventInformation.Builder other) {
    return new EventInformation.Builder(other);
  }
  
  /** Creates a new EventInformation RecordBuilder by copying an existing EventInformation instance */
  public static EventInformation.Builder newBuilder(EventInformation other) {
    return new EventInformation.Builder(other);
  }
  
  /**
   * RecordBuilder for EventInformation instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EventInformation>
    implements org.apache.avro.data.RecordBuilder<EventInformation> {

    private java.lang.String eventId;
    private java.lang.String eventOutbId;
    private java.lang.String eventName;
    private java.lang.String eventGrade;
    private long svcTgtSeq;
    private long spotDevSeq;
    private java.util.Map<java.lang.String,java.lang.Object> eventItems;

    /** Creates a new Builder */
    private Builder() {
      super(EventInformation.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(EventInformation.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.eventId)) {
        this.eventId = data().deepCopy(fields()[0].schema(), other.eventId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.eventOutbId)) {
        this.eventOutbId = data().deepCopy(fields()[1].schema(), other.eventOutbId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.eventName)) {
        this.eventName = data().deepCopy(fields()[2].schema(), other.eventName);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.eventGrade)) {
        this.eventGrade = data().deepCopy(fields()[3].schema(), other.eventGrade);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.svcTgtSeq)) {
        this.svcTgtSeq = data().deepCopy(fields()[4].schema(), other.svcTgtSeq);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.spotDevSeq)) {
        this.spotDevSeq = data().deepCopy(fields()[5].schema(), other.spotDevSeq);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.eventItems)) {
        this.eventItems = data().deepCopy(fields()[6].schema(), other.eventItems);
        fieldSetFlags()[6] = true;
      }
    }
    
    /** Creates a Builder by copying an existing EventInformation instance */
    private Builder(EventInformation other) {
            super(EventInformation.SCHEMA$);
      if (isValidValue(fields()[0], other.eventId)) {
        this.eventId = data().deepCopy(fields()[0].schema(), other.eventId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.eventOutbId)) {
        this.eventOutbId = data().deepCopy(fields()[1].schema(), other.eventOutbId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.eventName)) {
        this.eventName = data().deepCopy(fields()[2].schema(), other.eventName);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.eventGrade)) {
        this.eventGrade = data().deepCopy(fields()[3].schema(), other.eventGrade);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.svcTgtSeq)) {
        this.svcTgtSeq = data().deepCopy(fields()[4].schema(), other.svcTgtSeq);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.spotDevSeq)) {
        this.spotDevSeq = data().deepCopy(fields()[5].schema(), other.spotDevSeq);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.eventItems)) {
        this.eventItems = data().deepCopy(fields()[6].schema(), other.eventItems);
        fieldSetFlags()[6] = true;
      }
    }

    /** Gets the value of the 'eventId' field */
    public java.lang.String getEventId() {
      return eventId;
    }
    
    /** Sets the value of the 'eventId' field */
    public EventInformation.Builder setEventId(java.lang.String value) {
      validate(fields()[0], value);
      this.eventId = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'eventId' field has been set */
    public boolean hasEventId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'eventId' field */
    public EventInformation.Builder clearEventId() {
      eventId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'eventOutbId' field */
    public java.lang.String getEventOutbId() {
      return eventOutbId;
    }
    
    /** Sets the value of the 'eventOutbId' field */
    public EventInformation.Builder setEventOutbId(java.lang.String value) {
      validate(fields()[1], value);
      this.eventOutbId = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'eventOutbId' field has been set */
    public boolean hasEventOutbId() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'eventOutbId' field */
    public EventInformation.Builder clearEventOutbId() {
      eventOutbId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'eventName' field */
    public java.lang.String getEventName() {
      return eventName;
    }
    
    /** Sets the value of the 'eventName' field */
    public EventInformation.Builder setEventName(java.lang.String value) {
      validate(fields()[2], value);
      this.eventName = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'eventName' field has been set */
    public boolean hasEventName() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'eventName' field */
    public EventInformation.Builder clearEventName() {
      eventName = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'eventGrade' field */
    public java.lang.String getEventGrade() {
      return eventGrade;
    }
    
    /** Sets the value of the 'eventGrade' field */
    public EventInformation.Builder setEventGrade(java.lang.String value) {
      validate(fields()[3], value);
      this.eventGrade = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'eventGrade' field has been set */
    public boolean hasEventGrade() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'eventGrade' field */
    public EventInformation.Builder clearEventGrade() {
      eventGrade = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'svcTgtSeq' field */
    public java.lang.Long getSvcTgtSeq() {
      return svcTgtSeq;
    }
    
    /** Sets the value of the 'svcTgtSeq' field */
    public EventInformation.Builder setSvcTgtSeq(long value) {
      validate(fields()[4], value);
      this.svcTgtSeq = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'svcTgtSeq' field has been set */
    public boolean hasSvcTgtSeq() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'svcTgtSeq' field */
    public EventInformation.Builder clearSvcTgtSeq() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'spotDevSeq' field */
    public java.lang.Long getSpotDevSeq() {
      return spotDevSeq;
    }
    
    /** Sets the value of the 'spotDevSeq' field */
    public EventInformation.Builder setSpotDevSeq(long value) {
      validate(fields()[5], value);
      this.spotDevSeq = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'spotDevSeq' field has been set */
    public boolean hasSpotDevSeq() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'spotDevSeq' field */
    public EventInformation.Builder clearSpotDevSeq() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'eventItems' field */
    public java.util.Map<java.lang.String,java.lang.Object> getEventItems() {
      return eventItems;
    }
    
    /** Sets the value of the 'eventItems' field */
    public EventInformation.Builder setEventItems(java.util.Map<java.lang.String,java.lang.Object> value) {
      validate(fields()[6], value);
      this.eventItems = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'eventItems' field has been set */
    public boolean hasEventItems() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'eventItems' field */
    public EventInformation.Builder clearEventItems() {
      eventItems = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public EventInformation build() {
      try {
        EventInformation record = new EventInformation();
        record.eventId = fieldSetFlags()[0] ? this.eventId : (java.lang.String) defaultValue(fields()[0]);
        record.eventOutbId = fieldSetFlags()[1] ? this.eventOutbId : (java.lang.String) defaultValue(fields()[1]);
        record.eventName = fieldSetFlags()[2] ? this.eventName : (java.lang.String) defaultValue(fields()[2]);
        record.eventGrade = fieldSetFlags()[3] ? this.eventGrade : (java.lang.String) defaultValue(fields()[3]);
        record.svcTgtSeq = fieldSetFlags()[4] ? this.svcTgtSeq : (java.lang.Long) defaultValue(fields()[4]);
        record.spotDevSeq = fieldSetFlags()[5] ? this.spotDevSeq : (java.lang.Long) defaultValue(fields()[5]);
        record.eventItems = fieldSetFlags()[6] ? this.eventItems : (java.util.Map<java.lang.String,java.lang.Object>) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
