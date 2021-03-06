package com.luckmerlin.file.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.luckmerlin.adapter.OnSectionLoadFinish;
import com.luckmerlin.adapter.recycleview.ItemSlideRemover;
import com.luckmerlin.adapter.recycleview.ItemTouchInterrupt;
import com.luckmerlin.adapter.recycleview.OnItemTouchResolver;
import com.luckmerlin.adapter.recycleview.SectionListAdapter;
import com.luckmerlin.adapter.recycleview.SectionRequest;
import com.luckmerlin.core.Canceler;
import com.luckmerlin.file.Client;
import com.luckmerlin.file.Folder;
import com.luckmerlin.file.Path;
import com.luckmerlin.file.Query;
import com.luckmerlin.file.R;
import com.luckmerlin.file.api.OnApiFinish;
import com.luckmerlin.file.api.Reply;
import com.luckmerlin.file.api.What;
import com.luckmerlin.file.databinding.ItemListFileBinding;
import java.util.List;

public class FileBrowserAdapter extends SectionListAdapter<Query, Path> implements OnItemTouchResolver {
    private final ObservableField<Client> mCurrentClient=new ObservableField<Client>();

    @Override
    protected void onResolveFixedViewItem(RecyclerView recyclerView) {
        Context context=null!=recyclerView?recyclerView.getContext():null;
        if (null!=context){
            setFixHolder(TYPE_EMPTY,generateViewHolder(context,R.layout.item_content_empty));
        }
    }

    @Override
    protected final Canceler onNextSectionLoad(SectionRequest<Query> request, OnSectionLoadFinish<Query, Path> callback, String s) {
        Client client=mCurrentClient.get();
        return null!=client?client.onNextSectionLoad(request, (OnApiFinish<Reply<Folder<Query,Path>>>)(int what, String note, Reply<Folder<Query,Path>> data, Object arg)-> {
                boolean succeed=what== What.WHAT_SUCCEED&&null!=data&&data.isSuccess();
                Folder<Query,Path> folder=null!=data?data.getData():null;
                if (null!=callback){
                    callback.onSectionLoadFinish(succeed,note,folder);
                }
                onSectionLoadFinish(succeed,folder);
        }, s):null;
    }

    protected void onSectionLoadFinish(boolean succeed,Folder<Query,Path> folder){
         //Do nothing
    }

    public final boolean browser(Query query,String debug){
        Query current=getLatestSectionArg();
        if (!((null==current&&null==query)||(null!=current&&null!=query&&current.equals(query)))){
            return resetSection(query,null,null,"While browser path changed "+(null!=debug?debug:"."));
        }
        return false;
    }

    protected void onClientChanged(Client client){
        //Do nothing
    }

    public final boolean setClient(Client client, String debug) {
        Client current=mCurrentClient.get();
        if (null==client&&null!=current){
            mCurrentClient.set(null);
            boolean succeed= reset(debug);
            onClientChanged(null);
            return succeed;
        }else if (null!=client&&!client.equals(current)){
            mCurrentClient.set(client);
            boolean succeed= reset(debug);
            onClientChanged(client);
            return succeed;
        }
        return false;
    }

    public final Client getCurrentClient() {
        return mCurrentClient.get();
    }

    public final ObservableField<Client> getBrowserClient() {
        return mCurrentClient;
    }

    public final boolean reset(String debug){
        Client client=mCurrentClient.get();
        clean("While client reset to NULL.");
        return null!=client&&resetSection(debug);
    }

    @Override
    protected final Object onResolveDataViewHolder(ViewGroup viewGroup) {
        return R.layout.item_list_file;
    }

    @Override
    public final RecyclerView.LayoutManager onResolveLayoutManager(RecyclerView rv) {
        return new LinearLayoutManager(rv.getContext());
    }

    @Override
    protected final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, ViewDataBinding binding, int i1, Path data, List<Object> list) {
        super.onBindViewHolder(viewHolder, i, binding, i1, data, list);
        if (null!=binding&&binding instanceof ItemListFileBinding){
            ItemListFileBinding fileBinding=(ItemListFileBinding)binding;
            fileBinding.setPath(data);
            fileBinding.setPosition(i+1);
        }
    }

    @Override
    public final Object onResolveItemTouch(RecyclerView recyclerView) {
        return new ItemTouchInterrupt(){
            @Override
            protected Integer onResolveSlide(RecyclerView.ViewHolder holder, RecyclerView.LayoutManager manager) {
                return new ItemSlideRemover().onResolveSlide(holder,manager);
            }
        };
    }

}
