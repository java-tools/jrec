/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sf.cb2xml.sablecc.node;

import net.sf.cb2xml.sablecc.analysis.*;

@SuppressWarnings("nls")
public final class AComp3UsagePhrase extends PUsagePhrase
{
    private TComp3 _comp3_;

    public AComp3UsagePhrase()
    {
        // Constructor
    }

    public AComp3UsagePhrase(
        @SuppressWarnings("hiding") TComp3 _comp3_)
    {
        // Constructor
        setComp3(_comp3_);

    }

    @Override
    public Object clone()
    {
        return new AComp3UsagePhrase(
            cloneNode(this._comp3_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAComp3UsagePhrase(this);
    }

    public TComp3 getComp3()
    {
        return this._comp3_;
    }

    public void setComp3(TComp3 node)
    {
        if(this._comp3_ != null)
        {
            this._comp3_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comp3_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._comp3_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._comp3_ == child)
        {
            this._comp3_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._comp3_ == oldChild)
        {
            setComp3((TComp3) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
