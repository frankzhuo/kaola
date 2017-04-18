package com.kaola.edata.common.pageTool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 分页工具类。
 * 页面序号从1开始，第一页:pageIndex=1，第二页：pageIndex=2 ...
 * @version 1.00 2010-4-24
 * @since 1.5
 * @author ZhangShixi
 */
public class Page<T> implements Externalizable, Cloneable {

	private static final long serialVersionUID = -6192319941595582550L;

	// 当前页面的页数
	private int pageIndex;
	// 页面大小
	private int pageSize;
	// 数据总量
	private int totalData = -1;
	// 剩余数据量
	private int surplusData;
	// 页面总量
	private int totalPage;
	// 是否仅取第一页
	private boolean firstPage;
	// 是否可以工作。默认为false，只有设置了数据总量才为true。
	private boolean ready = true;

	protected String order = null;//设置排序字段，多个字段用，隔开 格式（name:desc,createDate:asc）

	//查询的数据
	private List<T> listData = new ArrayList<T>();

	// 默认当前页面页数为第一页
	private static final int DEFAULT_PAGE_INDEX = 1;
	// 默认页面大小为10
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 以默认当前页面和页面大小构造一个分页对象。
	 * 其中，默认当前页数为1，默认页面大小为10。
	 */
	public Page() {
		this.pageIndex = DEFAULT_PAGE_INDEX;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	/**
	 * 
	 * @return
	 */
	public static Page getEmptyPage() {
		Page localPage = new Page();
		localPage.setPageIndex(1);
		localPage.setListData(new ArrayList());
		localPage.setTotalData(1);
		localPage.setPageSize(1);
		return localPage;
	}

	/**
	 * 以指定的当前页面页数和页面大小构造一个分页对象。
	 * @param pageIndex 当前页数，若参数值不大于0，则使用默认值1。
	 * @param pageSize 页面大小，若参数值不大于0，则使用默认值10。
	 */
	public Page(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex > 0 ? pageIndex : DEFAULT_PAGE_INDEX;
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	/**
	 * 以指定的页面大小构造一个表示第一页的分页对象。
	 * @param pageSize 页面大小，若参数值不大于0，则使用默认值10。
	 * @return 构造好的第一页分页对象。
	 */
	public static Page newFirstPage(int pageSize) {
		Page page = new Page(1, pageSize);
		page.setFirstPage(true);
		page.setTotalData(pageSize);

		return page;
	}

	/**
	 * 获取数据总量。
	 * @return 数据总量。未设置时，会返回初始值-1。
	 */
	public int getTotalData() {
		return totalData;
	}

	/**
	 * 设置数据总量。在使用时，需提前调用此方法进行设置。
	 * 当数据总量设置好之后，会计算页面总量、修正当前页面页数、计算剩余数据量，
	 * 并设置该分页对象已经准备好，可以正常工作。
	 * @param totalData 数据总量。
	 */
	public void setTotalData(int totalData) {
		this.totalData = totalData;

		this.totalPage = this.totalData / pageSize + (this.totalData % pageSize == 0 ? 0 : 1);

//		if (this.pageIndex > this.totalPage) {
//			this.pageIndex = this.totalPage;
//		}

		this.surplusData = this.totalData - (this.pageIndex - 1) * this.pageSize;

		this.ready = true;
	}

	/**
	 * 获取当前分页对象的页面范围，包含当前页面的起始行和结束行。
	 * 如果未先调用setTotalData()方法设置数据总量，则会抛出运行时异常。
	 * @return 当前分页对象的页面范围。
	 * @throws java.lang.IllegalArgumentException 异常。
	 */
	public PageScope getPageScope() throws IllegalArgumentException {
		if (!ready) {
			throw new IllegalArgumentException("Page has not seted data amount.");
		}

		if (this.pageIndex > this.totalPage) {
			return null;
		}

		PageScope scope = new PageScope();
		int startLine = (this.pageIndex - 1) * this.pageSize;
		int endLine;
		if (this.surplusData < this.pageSize) {
			endLine = startLine + this.surplusData - 1;
		} else {
			endLine = startLine + this.pageSize - 1;
		}
		if (startLine < 0) {
			startLine = 0;
		}
		if (endLine < 0) {
			endLine = 0;
		}
		scope.setStartLine(startLine);
		scope.setEndLine(endLine);

		return scope;
	}

	/**
	 * 获取当前页面页数。
	 * @return 当前页面页数。
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 设置当前页面页数。
	 * @param pageIndex 当前页面页数。
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 获取页面大小。
	 * @return 页面大小。
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面大小。
	 * @param pageSize 页面大小。
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总页数。
	 * @return 总页数。
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 判断当前分页对象是否代表第一页。
	 * @return <code>true</code>代表是第一页，<code>false</code>不是第一页。
	 */
	public boolean isFirstPage() {
		return firstPage;
	}

	/**
	 * 设置当前分页对象是否为第一页。
	 * @param firstPage 是否是第一页。
	 *  <code>true</code>代表是第一页，<code>false</code>不是第一页。
	 */
	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * 重写父类的clone()方法。
	 * @return 新的分页对象。
	 * @throws java.lang.CloneNotSupportedException 不支持克隆异常。
	 *  如果对象的类不支持Cloneable接口，则重写clone方法的子类也会抛出此异常，
	 *  以指示无法复制某个实例。
	 */
	@Override
	protected Page clone() throws CloneNotSupportedException {
		return (Page) super.clone();
	}

	/**
	 * 重写父类的equals()方法，根据当前页面序号和页面大小比较两个分页对象是否相等。
	 * @param obj 比较对象，如果不是Page实例，将返回<code>false</code>。
	 * @return 两个对象是否相等。
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Page)) {
			return false;
		}
		final Page page = (Page) obj;
		if (page.getPageIndex() == this.pageIndex && page.getPageSize() == this.pageSize) {
			return true;
		}
		return false;
	}

	/**
	 * 重写父类的hashCode()方法，根据当前页面序号和页面大小生成一个唯一的哈希吗。
	 * @return 根据当前页面序号和页面大小生成一个唯一的哈希吗。
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.pageIndex ^ (this.pageIndex >>> 32));
		result = 37 * result + (this.pageSize ^ (this.pageSize >>> 32));

		return result;
	}

	/**
	 * 重写父类的toString()方法，返回分页信息的字符串表示。
	 * @return 分页信息的字符串表示。
	 *  该字符串由当前页面序号、页面大小、数据总量和是否准备好组成。
	 */
	@Override
	public String toString() {
		StringBuilder pageInfo = new StringBuilder();
		pageInfo.append(this.getClass());
		pageInfo.append("[");
		pageInfo.append("pageIndex=");
		pageInfo.append(this.pageIndex);
		pageInfo.append(", ");
		pageInfo.append("pageSize=");
		pageInfo.append(this.pageSize);
		pageInfo.append(", ");
		pageInfo.append("totalData=");
		pageInfo.append(this.totalData);
		pageInfo.append(", ");
		pageInfo.append("ready=");
		pageInfo.append(this.ready);
		pageInfo.append("]");

		return pageInfo.toString();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.pageIndex);
		out.writeInt(this.pageSize);
		out.writeInt(this.totalData);
		out.writeInt(this.surplusData);
		out.writeInt(this.totalPage);
		out.writeBoolean(this.firstPage);
		out.writeBoolean(this.ready);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.pageIndex = in.readInt();
		this.pageSize = in.readInt();
		this.totalData = in.readInt();
		this.surplusData = in.readInt();
		this.totalPage = in.readInt();
		this.firstPage = in.readBoolean();
		this.ready = in.readBoolean();
	}

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(order);
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
