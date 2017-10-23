package net.sf.JRecord.detailsBasic;

import java.util.List;

import net.sf.JRecord.Common.IFieldDetail;
import net.sf.JRecord.cgen.def.IArrayAnyDimension;
import net.sf.JRecord.detailsBasic.ItemDtl.ItemType;

/**
 * Extended Cobol-Item Definition
 * @author bruce
 *
 */
public interface IItemDetails {

	List<? extends IItemDetails> getChildItems();

	/**
	 * @return the fieldDefinition
	 */
	IFieldDetail getFieldDefinition();

	/**
	 * @return the arrayDefinition
	 */
	IArrayAnyDimension getArrayDefinition();

	/**
	 * @return the itemType
	 */
	ItemType getItemType();

	boolean isLeaf();
	
	int getLevelIndex();
}