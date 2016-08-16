package me.tatarka.bindingcollectionadapter;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.AdapterView;

import java.util.List;

/**
 * All the BindingAdapters so that you can set your adapters and items directly in your layout.
 */
public class BindingCollectionAdapters {
    // AdapterView
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "itemTypeCount", "items", "adapter", "itemDropDownLayout", "itemIds", "itemIsEnabled"}, requireAll = false)
    public static <T> void setAdapter(AdapterView adapterView, ItemBinding<T> itemBinding, Integer itemTypeCount, List items, BindingListViewAdapter<T> adapter, @LayoutRes int itemDropDownLayout, BindingListViewAdapter.ItemIds<? super T> itemIds, BindingListViewAdapter.ItemIsEnabled<? super T> itemIsEnabled) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("onItemBind must not be null");
        }
        BindingListViewAdapter<T> oldAdapter = (BindingListViewAdapter<T>) adapterView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                int count = itemTypeCount != null ? itemTypeCount : 1;
                adapter = new BindingListViewAdapter<>(count);
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);
        adapter.setDropDownItemLayout(itemDropDownLayout);
        adapter.setItems(items);
        adapter.setItemIds(itemIds);
        adapter.setItemIsEnabled(itemIsEnabled);

        if (oldAdapter != adapter) {
            adapterView.setAdapter(adapter);
        }
    }

    // ViewPager
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "items", "adapter", "pageTitles", "tabLayout"}, requireAll = false)
    public static <T> void setAdapter(ViewPager viewPager, ItemBinding<T> itemBinding, List items, BindingViewPagerAdapter<T> adapter, BindingViewPagerAdapter.PageTitles<T> pageTitles , BindingTabLayout tabLayout) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("onItemBind must not be null");
        }
        BindingViewPagerAdapter<T> oldAdapter = (BindingViewPagerAdapter<T>) viewPager.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingViewPagerAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);
        adapter.setItems(items);
        adapter.setPageTitles(pageTitles);

        if (oldAdapter != adapter) {
            viewPager.setAdapter(adapter);
        }
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }
    
    @BindingConversion
    public static <T> ItemBinding<T> toItemBinding(OnItemBind<T> onItemBind) {
        return ItemBinding.of(onItemBind);
    }
}
