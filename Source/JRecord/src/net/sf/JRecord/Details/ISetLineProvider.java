package net.sf.JRecord.Details;

public interface ISetLineProvider<Layout extends AbstractLayoutDetails, L extends AbstractLine> {

    /**
     * Set the line provider
     *
     * @param pLineProvider The lineProvider to set.
     */
    public abstract void setLineProvider(LineProvider<Layout, ? extends L> pLineProvider);
}
