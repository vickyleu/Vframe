package com.vickyleu.library.Base.Controller;//package com.vickyleu.library.Base.Controller;
//

//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.vickyleu.library.Base.Controller.CallBack.OnItemCallBack;
//import com.vickyleu.library.Base.Controller.CallBack.OnItemClickListener;
//import com.vickyleu.library.Base.Controller.CallBack.OnItemLongClickListener;
//import com.vickyleu.library.Base.Controller.CallBack.OnViewClickListener;
//import com.vickyleu.library.Base.Presenter.CycleCallBack;
//import com.vickyleu.library.Base.Presenter.IPresenter;
//import com.vickyleu.library.Base.model.Merge.IP_C;
//import com.vickyleu.library.Model.DataBaseCenter;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//

//public abstract class BaseRecyclerMapAdapter<T,D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements OnItemCallBack<T>, OnViewClickListener<VH>, OnItemClickListener<T>, OnItemLongClickListener<T> {
//    protected Map<T,D> list;
//    protected Context mContext;
//    protected BaseViewType[] viewTypes;
//    OnItemCallBack callBack;
//
//    /**
//     * click listener
//     */
//    protected OnItemClickListener mOnItemClickListener;
//    /**
//     * long click listener
//     */
//    protected OnItemLongClickListener mOnItemLongClickListener;
//    int _req = -1;
//    OnViewClickListener mOnViewClickListener;
//    private DataBaseCenter db = null;
//    private Map<Integer, IPresenter> presenterMap = new HashMap<>();
//    private CycleCallBack cycle;
//    private IP_C ip_c;
//
//
//    public BaseRecyclerMapAdapter(Map<T,D> list, Context mContext) {
//        this.list = list;
//        this.mContext = mContext;
//        callBack = this;
//        initAdapter(-1);
//    }
//
//
//    public final void registerIView(IP_C ip_c) {
//        this.ip_c = ip_c;
//    }
//
//    /**
//     * set a long click listener
//     */
//    public void setOnItemLongClickListener() {
//        mOnItemLongClickListener = this;
//    }
//
//    /**
//     * set a long click listener
//     */
//    public void setOnViewClickListener(final View view, final VH vh) {
//        mOnViewClickListener = this;
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnViewClickListener.OnViewClickListener(vh, view, vh.getAdapterPosition());
//            }
//        });
//    }
//
//
//    public DataBaseCenter getHelper() throws Exception {
//        if (db == null) db = DataBaseCenter.getDBInstance(mContext);
//        return db;
//    }
//
//    public void ReleaseHelper() throws Exception {
//        if (db != null) db.close();
//    }
//
//    /**
//     * set a click listener
//     */
//    public void setOnItemClickListener() {
//        mOnItemClickListener = this;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return getViewType(position);
//    }
//
//    protected abstract void initAdapter(int count, BaseViewType... viewTypes);
//
//    @Override
//    public VH onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        VH vh = null;
//
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (viewType < 0) {
//            vh = singleType(inflater, viewGroup);
//        } else {
//            vh = moreType(inflater, viewType, viewGroup);
//        }
//
//        return vh;
//    }
//
//    public void setViewType(int count, BaseViewType... viewTypes) {
//        if (count <= 0 || viewTypes == null) return;
//        this.viewTypes = new BaseViewType[viewTypes.length];
//        for (int i = 0; i < count; i++) {
//            this.viewTypes[i] = viewTypes[i];
//        }
//
//    }
//
//    protected int getViewType(int position) {
//        if (viewTypes == null) return -1;
//        for (int i = 0; i < viewTypes.length; i++) {
//            BaseViewType tp = viewTypes[i];
//            if (tp.getMin() <= position && tp.getMax() >= position) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public Map<T,D> getList() {
//        return list;
//    }
//
//    protected abstract VH singleType(LayoutInflater inflater, ViewGroup viewGroup);
//
//    protected abstract VH moreType(LayoutInflater inflater, int viewType, ViewGroup viewGroup);
//
//    @Override
//    public void onBindViewHolder(VH vh, int position) {
//        list.
//        bindView(vh, position, getViewType(position), list.get(position));
//        bindView(vh, position, getViewType(position));
//        bindItemViewClickListener(vh, getList().get(position));
//    }
//
//    protected <V extends View, D> void setPresenter(IPresenter<V, D> presenter, int resId) {
//        presenterMap.put(resId, presenter);
//        cycle = presenter.getCallBack();
//    }
//
//    protected IPresenter getPresenter(int key) {
//        return presenterMap.get(key);
//    }
//
//
//    /**
//     * bind click listner to itemview
//     *
//     * @param vh   viewholder
//     * @param item item
//     */
//    protected final void bindItemViewClickListener(final VH vh, final T item) {
//        if (mOnItemClickListener != null) {
//
//            vh.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickListener.onItemClick(callBack, view, item, vh.getAdapterPosition());
//                }
//            });
//        }
//        if (mOnItemLongClickListener != null) {
//            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mOnItemLongClickListener.onItemLongClick(callBack, v, item, vh.getAdapterPosition());
//                    return true;
//                }
//            });
//        }
//    }
//
//    protected void refreshView(View view, VH vh, T t, int layout, int position) {
//        refreshIView(position, vh, t, view.getId(), layout);
//    }
//
//    protected void refreshView(int position, VH vh, T t, int resId, int layout) {
//        refreshIView(position, vh, t, resId, layout);
//    }
//
//    private final void refreshIView(int position, VH vh, T t, int resId, int layout) {
//        if (ip_c!=null)
//        ip_c.notifyView(position, vh, t, resId, layout);
//    }
//
//    protected abstract void bindView(VH vh, int position, int viewType);
//
//    protected abstract void bindView(VH vh, int position, int viewType, T t);
//
//
//    @Override
//    public void onItemClick(OnItemCallBack requestAdapter, View view, T item, int position) {
//        requestAdapter.BaseItemClick(view, item, position);
//    }
//
//    @Override
//    public void onItemLongClick(OnItemCallBack requestAdapter, View view, T item, int position) {
//        requestAdapter.BaseItemLongClick(view, item, position);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (list == null || list.isEmpty()) return 0;
//        return list.size();
//    }
//
//    public void setItem(int position, T t) {
//        list.set(position, t);
//        notifyItemChanged(position);
//    }
//
//    public void setAllData(List<T> list) {
//        if (list == null || list.isEmpty()) {
//            this.list.clear();
//        } else {
//            this.list = list;
//        }
//        notifyDataSetChanged();
//    }
//
//    public void cleanData() {
//        this.list.clear();
//        notifyDataSetChanged();
//    }
//
//    public void insertItem(int position, T t) {
//        this.list.add(position, t);
//        notifyItemInserted(position);
//    }
//
//    public void deleteItem(int position) {
//        list.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    @Override
//    public void BaseItemClick(View view, T item, int position) {
//        ListItemClick(view, item, position);
//    }
//
//    @Override
//    public void BaseItemLongClick(View view, T item, int position) {
//        ListItemLongClick(view, item, position);
//    }
//
//    protected abstract void ListItemClick(View view, T item, int position);
//
//    protected abstract void ListItemLongClick(View view, T item, int position);
//
//
//    protected void onDestory() {
//        cycle.onDestroy();
//    }
//
//
//    public abstract void receiverNotify(int position, VH vh, T t, int resId, int layout);
//
//    @Override
//    public void OnViewClickListener(VH vh, View view, int position) {
//
//    }
//}