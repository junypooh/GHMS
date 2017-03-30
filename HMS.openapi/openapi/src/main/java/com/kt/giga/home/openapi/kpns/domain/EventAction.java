/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.kt.giga.home.openapi.kpns.domain;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class EventAction extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"EventAction\",\"namespace\":\"com.kt.iot.core.integration.avro\",\"fields\":[{\"name\":\"eventInformation\",\"type\":{\"type\":\"record\",\"name\":\"EventInformation\",\"fields\":[{\"name\":\"eventId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventOutbId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventName\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"eventGrade\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"svcTgtSeq\",\"type\":\"long\"},{\"name\":\"spotDevSeq\",\"type\":\"long\"},{\"name\":\"eventItems\",\"type\":{\"type\":\"map\",\"values\":[\"int\",\"long\",\"float\",\"double\",{\"type\":\"string\",\"avro.java.string\":\"String\"},\"boolean\"],\"avro.java.string\":\"String\"}}]}},{\"name\":\"actionTaskList\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"ActionTask\",\"fields\":[{\"name\":\"actionCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"actionTarget\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"taskResources\",\"type\":{\"type\":\"map\",\"values\":[\"int\",\"long\",\"float\",\"double\",{\"type\":\"string\",\"avro.java.string\":\"String\"},\"boolean\"],\"avro.java.string\":\"String\"}}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public EventInformation eventInformation;
  @Deprecated public java.util.List<ActionTask> actionTaskList;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public EventAction() {}

  /**
   * All-args constructor.
   */
  public EventAction(EventInformation eventInformation, java.util.List<ActionTask> actionTaskList) {
    this.eventInformation = eventInformation;
    this.actionTaskList = actionTaskList;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return eventInformation;
    case 1: return actionTaskList;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: eventInformation = (EventInformation)value$; break;
    case 1: actionTaskList = (java.util.List<ActionTask>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'eventInformation' field.
   */
  public EventInformation getEventInformation() {
    return eventInformation;
  }

  /**
   * Sets the value of the 'eventInformation' field.
   * @param value the value to set.
   */
  public void setEventInformation(EventInformation value) {
    this.eventInformation = value;
  }

  /**
   * Gets the value of the 'actionTaskList' field.
   */
  public java.util.List<ActionTask> getActionTaskList() {
    return actionTaskList;
  }

  /**
   * Sets the value of the 'actionTaskList' field.
   * @param value the value to set.
   */
  public void setActionTaskList(java.util.List<ActionTask> value) {
    this.actionTaskList = value;
  }

  /** Creates a new EventAction RecordBuilder */
  public static EventAction.Builder newBuilder() {
    return new EventAction.Builder();
  }
  
  /** Creates a new EventAction RecordBuilder by copying an existing Builder */
  public static EventAction.Builder newBuilder(EventAction.Builder other) {
    return new EventAction.Builder(other);
  }
  
  /** Creates a new EventAction RecordBuilder by copying an existing EventAction instance */
  public static EventAction.Builder newBuilder(EventAction other) {
    return new EventAction.Builder(other);
  }
  
  /**
   * RecordBuilder for EventAction instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<EventAction>
    implements org.apache.avro.data.RecordBuilder<EventAction> {

    private EventInformation eventInformation;
    private java.util.List<ActionTask> actionTaskList;

    /** Creates a new Builder */
    private Builder() {
      super(EventAction.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(EventAction.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.eventInformation)) {
        this.eventInformation = data().deepCopy(fields()[0].schema(), other.eventInformation);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.actionTaskList)) {
        this.actionTaskList = data().deepCopy(fields()[1].schema(), other.actionTaskList);
        fieldSetFlags()[1] = true;
      }
    }
    
    /** Creates a Builder by copying an existing EventAction instance */
    private Builder(EventAction other) {
            super(EventAction.SCHEMA$);
      if (isValidValue(fields()[0], other.eventInformation)) {
        this.eventInformation = data().deepCopy(fields()[0].schema(), other.eventInformation);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.actionTaskList)) {
        this.actionTaskList = data().deepCopy(fields()[1].schema(), other.actionTaskList);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'eventInformation' field */
    public EventInformation getEventInformation() {
      return eventInformation;
    }
    
    /** Sets the value of the 'eventInformation' field */
    public EventAction.Builder setEventInformation(EventInformation value) {
      validate(fields()[0], value);
      this.eventInformation = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'eventInformation' field has been set */
    public boolean hasEventInformation() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'eventInformation' field */
    public EventAction.Builder clearEventInformation() {
      eventInformation = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'actionTaskList' field */
    public java.util.List<ActionTask> getActionTaskList() {
      return actionTaskList;
    }
    
    /** Sets the value of the 'actionTaskList' field */
    public EventAction.Builder setActionTaskList(java.util.List<ActionTask> value) {
      validate(fields()[1], value);
      this.actionTaskList = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'actionTaskList' field has been set */
    public boolean hasActionTaskList() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'actionTaskList' field */
    public EventAction.Builder clearActionTaskList() {
      actionTaskList = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public EventAction build() {
      try {
        EventAction record = new EventAction();
        record.eventInformation = fieldSetFlags()[0] ? this.eventInformation : (EventInformation) defaultValue(fields()[0]);
        record.actionTaskList = fieldSetFlags()[1] ? this.actionTaskList : (java.util.List<ActionTask>) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
