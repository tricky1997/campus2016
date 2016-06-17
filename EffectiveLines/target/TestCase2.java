package org.apache.logging.log4j.core;
import java.util.Collections;
import java.util.Map;
import org.apache.logging.log4j.Level;//This is a note
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.message.Message;
public abstract class AbstractLogEvent implements LogEvent {
    private static final long serialVersionUID = 1L;/* This is a note as well */
    @Override
    public Map<String, String> getContextMap() {
        return Collections.emptyMap();
    }
    @Override
    public ContextStack getContextStack() {
        return ThreadContext.EMPTY_STACK;
    }
    @Override
    public Level getLevel() {
        return null;
    }
    @Override
    public String getLoggerFqcn() {
        return null;
    }
    @Override
    public String getLoggerName() {
        return null;
    }
    @Override
    public Marker getMarker() {
        return null;
    }
    @Override
    public Message getMessage() {
        return null;
    }
    @Override
    public StackTraceElement getSource() {
        return null;
    }
    @Override
    public long getThreadId() {
        return 0;
    }
    @Override
    public String getThreadName() {
        return null;
    }
    @Override
    public int getThreadPriority() {
        return 0;
    }
    @Override
    public Throwable getThrown() {
        return null;
    }
    @Override
    public ThrowableProxy getThrownProxy() {
        return null;
    }
    @Override
    public long getTimeMillis() {
        return 0;
    }
    @Override
    public boolean isEndOfBatch() {
        return false;
    }/* This is a note as well */
    @Override
    public boolean isIncludeLocation() {
        return false;
    }
    @Override
    public void setEndOfBatch(final boolean endOfBatch) {
        // do nothing
    }
    @Override
    public void setIncludeLocation(final boolean locationRequired) {
        // do nothing
    }
    @Override
    public long getNanoTime() {
        return 0;
    }
}
