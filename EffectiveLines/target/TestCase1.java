/* Comments at new lines */

package org.apache.logging.log4j.core;

import org.apache.logging.log4j.status.StatusLogger;


public class AbstractLifeCycle implements LifeCycle {

    protected static final org.apache.logging.log4j.Logger LOGGER = StatusLogger.getLogger();

    private volatile LifeCycle.State state = LifeCycle.State.INITIALIZED;

    protected boolean equalsImpl(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LifeCycle other = (LifeCycle) obj;
        if (state != other.getState()) {
            return false;
        }
        return true;
    }

    @Override
    public LifeCycle.State getState() {
        return this.state;
    }

    protected int hashCodeImpl() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    public boolean isInitialized() {
        return this.state == LifeCycle.State.INITIALIZED;
    }

    @Override
    public boolean isStarted() {
        return this.state == LifeCycle.State.STARTED;
    }

    public boolean isStarting() {
        return this.state == LifeCycle.State.STARTING;
    }

    @Override
    public boolean isStopped() {
        return this.state == LifeCycle.State.STOPPED;
    }

    public boolean isStopping() {
        return this.state == LifeCycle.State.STOPPING;
    }

    protected void setStarted() {
        this.setState(LifeCycle.State.STARTED);
    }

    protected void setStarting() {
        this.setState(LifeCycle.State.STARTING);
    }

    protected void setState(final LifeCycle.State newState) {
        this.state = newState;
        // Need a better string than this.toString() for the message
        // LOGGER.trace("{} {}", this.state, this);
    }

    protected void setStopped() {
        this.setState(LifeCycle.State.STOPPED);
    }

    protected void setStopping() {
        this.setState(LifeCycle.State.STOPPING);
    }

    @Override
    public void initialize() {
        this.state = State.INITIALIZED;
    }

    @Override
    public void start() {
        this.setStarted();
    }

    @Override
    public void stop() {
        this.state = LifeCycle.State.STOPPED;
    }

}
