package com.hentica.app.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/** 树视图工具 */
public class TreeViewHelper {

	/** 节点选中情况被改变了 */
	public interface OnNodeCheckChangedListener {

		public void OnNodeCheckChanged(TreeNode node, boolean isChecked);
	}

	/** 节点被点击事件 */
	public interface OnNodeClickedListener {

		/**
		 * 节点被点击了
		 * 
		 * @param node
		 *            被点击的节点
		 */
		public void onNodeClicked(TreeNode node);
	}

	/** 显示模式 */
	public enum ViewMode {

		/** 显示完整树结构 */
		kFullTree,

		/** 以列表形式显示，不显示中间节点 */
		kList,
	}

	/** 选择模式 */
	public enum SelectMode {

		/** 单选模式 */
		kSingleSelect,

		/** 多选模式 */
		kMultipleSelect,
	}

	/** 列表视图 */
	protected ListView mListView;

	/** 节点显示适配器 */
	protected BaseTreeAdapter mAdapter;

	/** 节点选择事件 */
	protected OnNodeCheckChangedListener mOnNodeCheckChangedListener;

	/** 节点被点击事件 */
	protected OnNodeClickedListener mOnNodeClickedListener;

	/** 子节点点击是否需要被选中 */
	protected boolean mIsLeafNodeNeedSelected = true;

	/** 构造函数 */
	public TreeViewHelper(ListView listView) {

		BaseTreeAdapter adapter = new DefaultAdapter();
		mListView = listView;
		this.setAdapter(adapter);

		// 绑定事件
		this.setEvent();
	}

	/** 设置显示适配器 */
	public void setAdapter(BaseTreeAdapter adapter) {

		mAdapter = adapter;

		if (mListView != null && adapter != null) {

			mListView.setAdapter(adapter);
		}

		// 绑定事件
		this.bindAdapterEvent();
	}

	/** 当前显示模式 */
	public void setViewMode(ViewMode viewMode) {

		if (mAdapter != null) {

			mAdapter.setViewMode(viewMode);
		}
	}

	/** 选择模式 */
	public void setSelectMode(SelectMode selectMode) {

		if (mAdapter != null) {

			mAdapter.setSelectMode(selectMode);
		}
	}

	/** 是否显示root节点 */
	public void setIsShowRoot(boolean isShowRoot) {

		if (mAdapter != null) {

			mAdapter.setIsShowRoot(isShowRoot);
		}
	}

	/** 设置根节点，以树形结构显示 */
	public void setRootNode(TreeNode node) {

		if (mAdapter != null) {

			mAdapter.setRootNode(node);
		}
	}

	/** 取得根节点 */
	public TreeNode getRootNode() {

		return mAdapter != null ? mAdapter.getRootNode() : null;
	}

	/** 取得选中节点 */
	public List<TreeNode> getCheckedNodes() {

		return mAdapter != null ? mAdapter.getCheckedNodes() : null;
	}

	/** 刷新数据 */
	public void reloadData() {

		if (mAdapter != null) {

			mAdapter.setRootNode(mAdapter.getRootNode());
		}
	}

	/** 节点选择事件 */
	public OnNodeCheckChangedListener getOnNodeCheckChangedListener() {
		return mOnNodeCheckChangedListener;
	}

	/** 节点选择事件 */
	public void setOnNodeCheckChangedListener(OnNodeCheckChangedListener onNodeCheckChangedListener) {
		mOnNodeCheckChangedListener = onNodeCheckChangedListener;

		// 绑定事件
		this.bindAdapterEvent();
	}

	/** 节点被点击事件 */
	public OnNodeClickedListener getOnNodeClickedListener() {
		return mOnNodeClickedListener;
	}

	/** 节点被点击事件 */
	public void setOnNodeClickedListener(OnNodeClickedListener onNodeClickedListener) {
		mOnNodeClickedListener = onNodeClickedListener;
	}

	public void setLeafNodeNeedSelected(boolean mLeafNodeNeedSelected) {
		mIsLeafNodeNeedSelected = mLeafNodeNeedSelected;
	}

	/** 设置事件 */
	protected void setEvent() {

		if (mListView != null) {

			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					if (mAdapter != null) {

						TreeNode node = (TreeNode) parent.getAdapter().getItem(position);

						// 父节点切换展开状态
						if (node.isParentNode()) {

							mAdapter.toggleExpand(position - mListView.getHeaderViewsCount());
						}
						// 子节点切换选中状态
						else {

							if(mIsLeafNodeNeedSelected){
								mAdapter.setNodeCheckedByTouched(node, !node.isChecked());
							}
						}

						if (mOnNodeClickedListener != null) {

							mOnNodeClickedListener.onNodeClicked(node);
						}
					}
				}
			});
		}
	}

	/** 绑定事件 */
	protected void bindAdapterEvent() {

		if (mAdapter != null && mOnNodeCheckChangedListener != null) {

			mAdapter.setOnNodeCheckChangedListener(mOnNodeCheckChangedListener);
		}
	}

	/** 适配器，用于显示树 */
	public static abstract class BaseTreeAdapter extends BaseAdapter {

		/** 根节点 */
		protected TreeNode mRootNode;

		/** 展开后的显示列表 */
		protected List<TreeNode> mShowedNodes = new ArrayList<TreeNode>();

		/** 是否显示root节点 */
		protected boolean mIsShowRoot = true;

		/** 节点选择事件，只由触摸时触发 */
		protected OnNodeCheckChangedListener mOnNodeCheckChangedListener;

		/** 当前显示模式 */
		protected ViewMode mViewMode = ViewMode.kFullTree;

		/** 选择模式 */
		protected SelectMode mSelectMode = SelectMode.kMultipleSelect;

		/** 是否正在触摸中 */
		protected boolean mIsOnTouching;

		public List<TreeNode> getShowedNodes(){
			return mShowedNodes;
		}
		public void clearShowedNodes() {
			//TODO
			mShowedNodes.clear();
		}
		/** 当前显示模式 */
		public ViewMode getViewMode() {
			return mViewMode;
		}

		/** 当前显示模式 */
		public void setViewMode(ViewMode viewMode) {
			mViewMode = viewMode;
		}

		/** 选择模式 */
		public SelectMode getSelectMode() {
			return mSelectMode;
		}

		/** 选择模式 */
		public void setSelectMode(SelectMode selectMode) {
			mSelectMode = selectMode;
		}

		/** 是否显示root节点 */
		public boolean isIsShowRoot() {
			return mIsShowRoot;
		}

		/** 是否显示root节点 */
		public void setIsShowRoot(boolean isShowRoot) {
			mIsShowRoot = isShowRoot;
		}

		/** 设置根节点 */
		public void setRootNode(TreeNode node) {

			mRootNode = node;
			this.reloadShowNodes();
			this.notifyDataSetChanged();
		}

		/** 取得根节点 */
		public TreeNode getRootNode() {

			return mRootNode;
		}

		/** 切换某节点位置的展开情况 */
		public void toggleExpand(int pos) {

			TreeNode node = this.getItem(pos);

			if (node != null && node.isParentNode()) {

				node.setExpanded(!node.isExpanded());
				this.reloadShowNodes();
				this.notifyDataSetChanged();
			}
		}

		/** 取得选中节点 */
		public List<TreeNode> getCheckedNodes() {

			List<TreeNode> result = new ArrayList<TreeNode>();
			this.getCheckedNodes(mRootNode, result);

			return result;
		}

		/** 设置某节点是否选中，由触摸事件触发 */
		public void setNodeCheckedByTouched(TreeNode node, boolean isChecked) {

			mIsOnTouching = true;
			this.setNodeChecked(node, isChecked);
			mIsOnTouching = false;
		}

		/** 设置某节点是否选中 */
		protected void setNodeChecked(TreeNode node, boolean isChecked) {

			// 若是多选模式
			if (mSelectMode == SelectMode.kMultipleSelect) {

				switch (mViewMode) {
				case kFullTree:
					// 所有可见子节点都执行
					this.setNodeCheckedWithVisibleChildren(node, isChecked);
					// 刷新选中状态
					this.refreshMultipTreeChecked(mRootNode);
					break;

				case kList:
					// 所有可见子节点都执行
					this.setVisibleNodeChecked(node, mShowedNodes, isChecked);
					// 刷新选中状态
					this.refreshMultipListChecked(mRootNode, mShowedNodes);
					break;
				}
			}
			// 若是单选模式
			else if (mSelectMode == SelectMode.kSingleSelect) {

				this.setNodeSingleChecked(node, mShowedNodes, isChecked);
			}

			// 刷新界面
			this.notifyDataSetChanged();
		}

		/** 设置某个节点的选中情况，不管子节点，能触发事件 */
		protected void _setNodeChecked2(TreeNode node, boolean isChecked) {

			if (node.isChecked() != isChecked) {

				node.setChecked(isChecked);

				// 发出事件，只在触摸过程中发出
				if (mIsOnTouching && mOnNodeCheckChangedListener != null) {

					mOnNodeCheckChangedListener.OnNodeCheckChanged(node, isChecked);
				}
			}
		}

		/** 取得选中的节点，包括子节点 */
		protected void getCheckedNodes(TreeNode node, List<TreeNode> result) {

			if (node != null && result != null) {

				if (node.isChecked()) {

					result.add(node);
				}

				// 添加子节点
				for (TreeNode child : node.getChildren()) {

					this.getCheckedNodes(child, result);
				}
			}
		}

		/** 设置某节点是否被选中，不管子节点 */
		protected void setNodeCheckedWithoutChildren(TreeNode node, boolean isChecked) {

			if (node != null) {
				_setNodeChecked2(node, isChecked);

				// 若是取消选中，则取消所有父节点的选中
				if (!isChecked) {

					this.unCheckAllParent(node);
				}
			}
		}

		/**
		 * 设置某节点是否被选中，树型模式与列表模式均可；选中后取消其他所有选中项
		 * 
		 * @param node
		 *            要选中的节点
		 * @param showNodes
		 *            所有节点
		 * @param isChecked
		 *            是否选中
		 */
		protected void setNodeSingleChecked(TreeNode node, List<TreeNode> showNodes,
				boolean isChecked) {

			// 若是选中
			if (isChecked) {

				// 选中指定项
				_setNodeChecked2(node, isChecked);

				// 取消其他所有选中项
				for (TreeNode treeNode : showNodes) {

					if (treeNode != node) {

						_setNodeChecked2(treeNode, !isChecked);
					}
				}
			}
			// 只能选中，不能取消
		}

		/**
		 * 设置某节点是否被选中，仅列表模式下有用。<br />
		 * 若操作的是根节点，则所有显示的子节点都做相同操作；若操作的是子节点，则正常操作
		 * 
		 * @param node
		 *            指定节点
		 * @param showNodes
		 *            所有显示的节点
		 * @param isChecked
		 *            是否需要选中
		 */
		protected void setVisibleNodeChecked(TreeNode node, List<TreeNode> showNodes,
				boolean isChecked) {

			if (node != null && showNodes != null) {

				// 若操作的是根节点
				if (node == mRootNode) {

					// 所有节点选中状况一致
					for (TreeNode treeNode : showNodes) {

						_setNodeChecked2(treeNode, isChecked);
					}
				}
				// 若是叶子节点
				else if (!node.isParentNode()) {

					_setNodeChecked2(node, isChecked);
				}
			}
		}

		/** 设置某节点是否被选中，子节点做相同操作，仅针对显示出来的节点 */
		protected void setNodeCheckedWithVisibleChildren(TreeNode node, boolean isChecked) {

			// 仅针对显示出来的节点
			if (node != null && node.isVisible()) {

				// 若是中间节点
				if (node.isParentNode()) {

					_setNodeChecked2(node, isChecked);

					List<TreeNode> children = node.getChildren();

					if (children != null) {

						for (TreeNode child : children) {

							this.setNodeCheckedWithVisibleChildren(child, isChecked);
						}
					}
				}
				// 若是叶子节点
				else {

					_setNodeChecked2(node, isChecked);
				}

				// 若是取消选中
				if (!node.isChecked()) {

					// 取消所有父节点的选中状态
					this.unCheckAllParent(node);
				}
			}
		}

		/** 取消所有父节点的选中状态 */
		protected void unCheckAllParent(TreeNode node) {

			if (node != null) {

				TreeNode parent = node.getParent();
				while (parent != null) {

					// 所有父节点取消选中
					_setNodeChecked2(parent, false);
					parent = parent.getParent();
				}
			}
		}

		/** 刷新多行模式下的选中状况，若所有显示的子节点都选中了，则选中根节点 */
		protected void refreshMultipListChecked(TreeNode root, List<TreeNode> showedNodes) {

			// 若根节点不为空且显示节点不为空
			if (root != null && root.isParentNode()) {

				// 若有显示的子节点
				if (showedNodes != null && !showedNodes.isEmpty()) {

					// 根节点默认选中
					_setNodeChecked2(root, true);

					// 遍历所有子节点
					for (TreeNode showedChild : showedNodes) {

						// 若有子节点未选中，则根节点不选中
						if (!showedChild.isParentNode() && !showedChild.isChecked()) {

							_setNodeChecked2(root, false);
							break;
						}
					}
				}
			}
		}

		/** 刷新多选模式下的选中状况，若所有子节点都选中了，则选中当前节点 */
		protected void refreshMultipTreeChecked(TreeNode node) {

			if (node != null && node.isParentNode()) {

				// 若不是空节点
				if (node.hasChildren()) {

					// 递归遍历所有子节点
					for (TreeNode child : node.getChildren()) {

						refreshMultipTreeChecked(child);
					}

					// 若它的所有子节点都选中了，则选中本身
					if (TreeNodeHelper.isAllChildrenChecked(node)) {

						_setNodeChecked2(node, true);
					}
				}
				// // 若是空节点
				// else {
				//
				// // 注意：这里不是错误，而是特殊需求:若没有子节点，只能处于选中状态，且显示为未选中
				// node, true);
				// }
			}
		}

		/** 刷新单选模式下的选中状况，选中第一个节点 */
		protected void refreshSingleTreeChecked(TreeNode node) {

			List<TreeNode> allNodes = TreeNodeHelper.treeToList(node);

			// 第一个选中的节点
			TreeNode firstCheckedNode = null;

			for (TreeNode treeNode : allNodes) {

				// 若还未找到选中节点，且找到了选中节点
				if (firstCheckedNode == null && treeNode.isChecked()) {

					firstCheckedNode = treeNode;

				} else {

					_setNodeChecked2(treeNode, false);
				}
			}
		}

		@Override
		public int getCount() {
			return mShowedNodes.size();
		}

		@Override
		public TreeNode getItem(int id) {
			return id < mShowedNodes.size() ? mShowedNodes.get(id) : null;
		}

		@Override
		public long getItemId(int id) {
			return 0;
		}

		@Override
		public View getView(int id, View convertView, ViewGroup parent) {

			TreeNode node = this.getItem(id);
			return this.getView(node, convertView, parent);
		}

		/** 取得某节点对应的视图，由子类实现 */
		public abstract View getView(TreeNode node, View convertView, ViewGroup parent);

		/** 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
		public static int dip2px(Context context, float dpValue) {

			final float scale = context.getResources().getDisplayMetrics().density;
			return (int) (dpValue * scale + 0.5f);
		}

		/** 节点选择事件，只由触摸时触发 */
		public OnNodeCheckChangedListener getOnNodeCheckChangedListener() {
			return mOnNodeCheckChangedListener;
		}

		/** 节点选择事件，只由触摸时触发 */
		public void setOnNodeCheckChangedListener(
				OnNodeCheckChangedListener onNodeCheckChangedListener) {
			mOnNodeCheckChangedListener = onNodeCheckChangedListener;
		}

		/** 刷新显示树 */
		protected void reloadShowNodes() {

			mShowedNodes.clear();

			// 刷新显示节点
			switch (mViewMode) {
			case kFullTree:
				// 把树结构添加到显示列表中
				this.addVisibleNodesTreeToShowList(mRootNode, mShowedNodes);
				break;

			case kList:
				// 仅把根节点和叶子节点添加到显示列表中
				this.addVisibleNodesPointToShowList(mRootNode, mShowedNodes);
				break;
			}

			// 刷新选中状态
			// 若是单选模式
			if (mSelectMode == SelectMode.kSingleSelect) {

				// 选中第一个节点
				this.refreshSingleTreeChecked(mRootNode);
			}
			// 若是多选模式
			else if (mSelectMode == SelectMode.kMultipleSelect) {

				switch (mViewMode) {
				case kFullTree:
					// 刷新选中状态
					this.refreshMultipTreeChecked(mRootNode);
					break;

				case kList:
					// 刷新选中状态
					this.refreshMultipListChecked(mRootNode, mShowedNodes);
					break;
				}
			}
		}

		/**
		 * 仅把根节点和叶子节点添加到显示列表中，中间节点不添加到显示列表；只添加可见节点
		 * 
		 * @param rootNode
		 *            根节点
		 * @param showedNodes
		 *            保存显示结果
		 */
		protected void addVisibleNodesPointToShowList(TreeNode rootNode, List<TreeNode> showedNodes) {

			if (rootNode != null) {

				// 取得所有节点
				List<TreeNode> allNodes = TreeNodeHelper.treeToList(rootNode);

				// 若不显示根节点，且当前是根节点
				if (!mIsShowRoot && (mRootNode == rootNode)) {
					// 不加入到显示列表
				} else {

					// 根节点添加到显示列表中
					showedNodes.add(rootNode);
				}

				for (TreeNode treeNode : allNodes) {

					// 若是子节点，且可见，且根节点展开了，则添加到显示列表中
					if (!treeNode.isParentNode() && treeNode.isVisible() && rootNode.isExpanded()) {

						showedNodes.add(treeNode);
					}
				}
			}
		}

		/**
		 * 把某个节点(及子节点)加入显示列表，深度优先；只添加可见节点。注意，这里添加节点包括中间节点和叶子节点
		 * 
		 * @param node
		 *            父节点
		 * @param showedNodes
		 *            保存显示节点
		 */
		protected void addVisibleNodesTreeToShowList(TreeNode node, List<TreeNode> showedNodes) {

			if (node != null && node.isVisible()) {

				// 若不显示根节点，且当前是根节点
				if (!mIsShowRoot && (mRootNode == node)) {
					// 不加入到显示列表
				} else {

					// 把此节点加入显示列表
					showedNodes.add(node);
				}

				// 若有子节点，则把子节点也加入显示列表
				if (node.isParentNode() && node.isExpanded()) {

					List<TreeNode> children = node.getChildren();

					for (TreeNode child : children) {

						this.addVisibleNodesTreeToShowList(child, showedNodes);
					}
				}
			}
		}
	}

	/** 树节点 */
	public static class TreeNode {

		/** 节点显示的文字 */
		private String text;//
		/** 是否处于选中状态 */
		private boolean isChecked = false;
		/** 是否处于展开状态 */
		private boolean isExpanded = true;
		/** 是否拥有复选框 */
		private boolean hasCheckBox = true;
		/** 是否可见 */
		private boolean visible = true;
		/** 是否是目录节点 */
		private boolean isParentNode = false;
		/** 是否是最后一个节点 */
		private boolean isLastNode = false;
		/** 额外数据结构 */
		private Object mUserData;

		/** 父节点 */
		private TreeNode parent;
		/** 子节点 */
		private List<TreeNode> children = new ArrayList<TreeNode>();

		/** 显示内容 */
		public String getText() {
			return text;
		}

		/** 显示内容 */
		public void setText(String text) {
			this.text = text;
		}

		/** 父节点 */
		public TreeNode getParent() {
			return parent;
		}

		/** 设置父节点 */
		private void setParent(TreeNode parent) {
			this.parent = parent;
		}

		/** 取得子节点 */
		public List<TreeNode> getChildren() {
			return children;
		}

		public boolean isLastNode() {
			return isLastNode;
		}

		public void setLastNode(boolean mLastNode) {
			isLastNode = mLastNode;
		}

		/** 添加子节点 */
		public void addChildren(TreeNode node) {

			if (node != null) {

				children.add(node);
				node.setParent(this);
			}
		}

		/** 从父节点移除 */
		public void removeFromParent() {

			if (parent != null) {

				// 从父节点移除
				List<TreeNode> children = parent.getChildren();
				if (children != null) {

					children.remove(this);
				}
				// 父节点置空
				parent = null;
			}
		}

		/** 移除所有子节点 */
		public void removeAllChildren() {

			if (children != null) {

				children.clear();
			}
		}

		/** 是否选中 */
		public boolean isChecked() {
			return isChecked;
		}

		/** 是否选中 */
		public void setChecked(boolean isChecked) {

			// // 注意：这里不是错误，而是特殊需求:若没有子节点，只能处于选中状态，且显示为未选中
			// // 若没有子节点
			// if (this.isParentNode() && !this.hasChildren()) {
			//
			// // 强制转为选中状态
			// isChecked = true;
			// }
			this.isChecked = isChecked;
		}

		/** 是否展开 */
		public boolean isExpanded() {
			return isExpanded;
		}

		/** 是否展开 */
		public void setExpanded(boolean isExpanded) {
			this.isExpanded = isExpanded;
		}

		/** 是否有选择框 */
		public boolean isHasCheckBox() {
			return hasCheckBox;
		}

		/** 是否有选择框 */
		public void setHasCheckBox(boolean hasCheckBox) {
			this.hasCheckBox = hasCheckBox;
		}

		/** 取得层级 */
		public int getLevel() {

			int level = 0;

			TreeNode parent = this.parent;
			while (parent != null) {

				level++;
				parent = parent.getParent();
			}
			return level;
		}

		/** 是否可见 */
		public boolean isVisible() {
			return visible;
		}

		/** 是否可见 */
		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		/** 自己及所有父节点是否可见 */
		public boolean isVisibleWithParent() {

			boolean isVisible = this.isVisible();

			// 遍历所有父节点
			TreeNode parent = this.getParent();
			while (parent != null) {

				// 若有一个父节点不可见，则标记为不可见
				if (!parent.isVisible()) {

					isVisible = false;
					break;
				}
			}

			return isVisible;
		}

		/** 额外数据结构 */
		public Object getUserData() {
			return mUserData;
		}

		/** 额外数据结构 */
		public void setUserData(Object userData) {
			mUserData = userData;
		}

		/** 是否是中间节点，否则为叶子节点 */
		public boolean isParentNode() {

			return isParentNode;
		}

		/** 是否是中间节点，否则为叶子节点 */
		public void setIsParentNode(boolean isParentNode) {
			this.isParentNode = isParentNode;
		}

		/** 是否有子节点 */
		public boolean hasChildren() {

			return isParentNode && children != null && !children.isEmpty();
		}

		@Override
		public String toString() {

			StringBuilder result = new StringBuilder();

			for (int i = 0, level = this.getLevel(); i < level; i++) {

				result.append("  ");
			}
			result.append(getText() + (this.isChecked() ? " *" : ""));
			result.append("\n");

			if (this.isParentNode()) {

				for (TreeNode child : this.getChildren()) {

					result.append(child.toString());
				}
			}

			return result.toString();
		}
	}

	/** 树形节点辅助工具 */
	public static class TreeNodeHelper {

		/** 取得遍历结果，以列表形式返回，深度优先遍历 */
		public static List<TreeNode> treeToList(TreeNode root) {

			List<TreeNode> mAllNodes = new ArrayList<TreeNode>();
			addToList(root, mAllNodes);

			return mAllNodes;
		}

		/** 把所有节点添加到列表中 */
		private static void addToList(TreeNode node, List<TreeNode> allNodes) {

			if (node != null && allNodes != null) {

				// 把当前节点加入列表
				allNodes.add(node);

				// 把所有子节点加入列表
				List<TreeNode> children = node.getChildren();
				if (node.isParentNode() && children != null) {

					for (TreeNode child : children) {

						addToList(child, allNodes);
					}
				}
			}
		}

		/**
		 * 是否所有的子节点都被选中了<br />
		 * 若没有子节点，返回false
		 */
		public static boolean isAllChildrenChecked(TreeNode node) {

			if (node != null) {

				// 若是父节点
				if (node.isParentNode()) {

					// 若此节点没有子节点，则默认为未选中
					if (node.getChildren() == null || node.getChildren().isEmpty()) {

						return false;
					}

					// 递归遍历所有子节点，若有子节点未选中，则返回false
					for (TreeNode child : node.getChildren()) {

						// 若子节点是空节点
						if (child.isParentNode() && !child.hasChildren()) {

							// 若此子节点是未选中，返回false
							if (!child.isChecked()) {

								return false;
							}
						}
						// 若它的某个子节点有未选中的，返回false
						else if (!isAllChildrenChecked(child)) {

							return false;
						}
					}

					// 经过以上遍历，执行到这里，说明所有子节点都选中了
					return true;
				}
				// 若不是父节点，返回其本身的选中状况
				else {
					return node.isChecked();
				}
			}
			return false;
		}

		/** 深度拷贝节点，userData属性不拷贝 */
		public static TreeNode copyNode(TreeNode node) {

			if (node != null) {

				TreeNode newNode = new TreeNode();
				newNode.text = node.text;
				newNode.isChecked = node.isChecked;
				newNode.isExpanded = node.isExpanded;
				newNode.hasCheckBox = node.hasCheckBox;
				newNode.visible = node.visible;
				newNode.isParentNode = node.isParentNode;
				newNode.mUserData = node.mUserData;
				// newNode.parent = node.parent;
				newNode.children = new ArrayList<TreeNode>();

				for (TreeNode child : node.children) {

					TreeNode newChild = copyNode(child);
					newChild.setParent(newNode);

					newNode.addChildren(newChild);
				}
				return newNode;
			}
			return null;
		}
	}

	/** 默认视图适配器 */
	public static class DefaultAdapter extends BaseTreeAdapter {

		@Override
		public View getView(TreeNode node, View convertView, ViewGroup parent) {

//			if (convertView == null) {
//
//				convertView = View.inflate(parent.getContext(), R.layout.a_a04_tree_item, null);
//
//				// 选中事件
//				AQuery query = new AQuery(convertView);
//				query.id(R.id.e08_tree_right_icon_checkbox).getView()
//						.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//
//								// CheckBox checkbox = (CheckBox) v;
//								// boolean isChecked = checkbox.isChecked();
//								// checkbox, !isChecked);
//
//								TreeNode node = (TreeNode) v.getTag();
//								if (node != null) {
//
//									setNodeCheckedByTouched(node, !node.isChecked());
//								}
//							}
//						});
//			}
//
//			boolean isParent = node.isParentNode();
//
//			// 图标状态
//			AQuery query = new AQuery(convertView);
//
//			// 左侧展开图标
//			query.id(R.id.e08_tree_left_icon_checkbox).checked(node.isExpanded())
//					.visibility(isParent ? View.VISIBLE : View.INVISIBLE);
//
//			// // 注意：这里不是错误，而是特殊需求:若没有子节点，只能处于选中状态，且显示为未选中
//			// // 若是父节点且没有子节点
//			// if (node.isParentNode() && !node.hasChildren()) {
//			//
//			// // 显示为未选中
//			// query.id(R.id.common_tree_right_icon_checkbox).checked(false);
//			// }
//			// // 其他情况按照节点本身情况选中
//			// else {
//			//
//			// query.id(R.id.common_tree_right_icon_checkbox).checked(
//			// node.isChecked());
//			// }
//
//			// 右侧选中图标，没有定义checkbox时不显示、单选模式下中间节点不显示
//			boolean isRightIconVisible = true;
//			if (!node.isHasCheckBox()) {
//
//				isRightIconVisible = false;
//			}
//			int rightIconVisible = (isRightIconVisible ? View.VISIBLE : View.INVISIBLE);
//			ControlCheckBox rightCheckBox = (ControlCheckBox) query.id(
//					R.id.e08_tree_right_icon_checkbox).getView();
//			rightCheckBox.setVisibility(rightIconVisible);
//			rightCheckBox.setChecked(node.isChecked());
//			// 不允许触摸事件改变选中状态，只能由代码调用改变
//			rightCheckBox.setIsEnableTouchedClick(false);
//			// 把视图与节点数据关联起来
//			rightCheckBox.setTag(node);
//
//			// 中间节点文字
//			query.id(R.id.e08_tree_parent_node_text).text(node.getText())
//					.visibility(isParent ? View.VISIBLE : View.INVISIBLE);
//			// 叶子节点文字
//			query.id(R.id.e08_tree_child_node_text).text(node.getText())
//					.visibility(isParent ? View.INVISIBLE : View.VISIBLE);
//
//			// 计算层级偏移
//			// 默认按自己的层级偏移
//			int offsetLevel = node.getLevel();
//
//			// 若是列表模式
//			if (this.getViewMode() == ViewMode.kList) {
//
//				// 且是叶子节点
//				if (!node.isParentNode()) {
//
//					offsetLevel = 1;
//				}
//			}
//			// 层级偏移
//			query.id(R.id.e08_tree_left_icon_checkbox).getView()
//					.setPadding(offsetLevel * dip2px(parent.getContext(), 10), 0, 0, 0);

			return convertView;
		}
	}
}
