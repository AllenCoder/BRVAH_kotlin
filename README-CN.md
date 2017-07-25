# 这是Kotlin版本的BaseRecyclerViewAdapterHelper的使用Demo

# [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

一个强大并且灵活的RecyclerViewAdapter，欢迎使用。（喜欢的可以**Star**一下）
# 它能做什么？（[下载 apk](https://fir.im/s91g)）
- **优化Adapter代码（减少百分之70%代码）**
- **添加点击item点击、长按事件、以及item子控件的点击事件**
- **添加加载动画（一行代码轻松切换5种默认动画）**
- **添加头部、尾部、下拉刷新、上拉加载（感觉又回到ListView时代）**
- **设置自定义的加载更多布局**
- **添加分组（随心定义分组头部）**
- **自定义不同的item类型（简单配置、无需重写额外方法）**
- **设置空布局（比Listview的setEmptyView还要好用！）**
- **添加拖拽item**


# 如何使用它？
先在 build.gradle 的 repositories 添加:
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
然后在dependencies添加:
```
	dependencies {
	        compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.21'
	}
```

# 如何使用它来创建Adapter？

```java
class QuickAdapter(dataSize: Int) : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.layout_animation, DataServer.getSampleData(dataSize)) {

    override fun convert(helper: BaseViewHolder, item: Status) {
        when (helper.layoutPosition % 3) {
            0 -> helper.setImageResource(R.id.img, R.mipmap.animation_img1)
            1 -> helper.setImageResource(R.id.img, R.mipmap.animation_img2)
            2 -> helper.setImageResource(R.id.img, R.mipmap.animation_img3)
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro")
        helper.setText(R.id.tweetText, "O ever youthful,O ever weeping")

    }


}
```
Adapter
```java
 adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            Log.d(TAG, "onItemClick: ")
            Toast.makeText(this@ItemClickActivity, "onItemClick" + position, Toast.LENGTH_SHORT).show()
        }
        
```
# 设置 item  click  新增添加子布局多个控件的点击事件
# 设置 it item child click
首先需要添加需要点击触发的 childview id 
``` 
  override fun convert(helper: BaseViewHolder, item: ClickEntity) {

        when (helper.itemViewType) {
            ClickEntity.CLICK_ITEM_VIEW -> {
                helper.addOnClickListener(R.id.btn)
            }
            ClickEntity.CLICK_ITEM_CHILD_VIEW -> {
                helper.addOnClickListener(R.id.iv_num_reduce).addOnClickListener(R.id.iv_num_add)
                        .addOnLongClickListener(R.id.iv_num_reduce).addOnLongClickListener(R.id.iv_num_add)
            }
            ClickEntity.LONG_CLICK_ITEM_VIEW -> {
                helper.addOnLongClickListener(R.id.btn)
            }
            ClickEntity.LONG_CLICK_ITEM_CHILD_VIEW -> {
                helper.addOnLongClickListener(R.id.iv_num_reduce).addOnLongClickListener(R.id.iv_num_add)
                        .addOnClickListener(R.id.iv_num_reduce).addOnClickListener(R.id.iv_num_add)
            }
            ClickEntity.NEST_CLICK_ITEM_CHILD_VIEW -> {
                helper.setNestView(R.id.item_click) // u can set nestview id
                val recyclerView = helper.getView<RecyclerView>(R.id.nest_list)
                recyclerView.layoutManager = LinearLayoutManager(helper.itemView.context, LinearLayoutManager.VERTICAL, false)
                recyclerView.setHasFixedSize(true)

                nestAdapter = NestAdapter()
                nestAdapter.setOnItemClickListener(this)
                nestAdapter.setOnItemChildClickListener(this)
                recyclerView.adapter = nestAdapter
            }
        }
    }
```
Activity
```java
           mAnimationAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
               var content: String
               val status = adapter.getItem(position) as Status
               when (view.id) {
                   R.id.img -> {
                       content = "img:" + status.userAvatar
                       Toast.makeText(this@AnimationUseActivity, content, Toast.LENGTH_LONG).show()
                   }
                   R.id.tweetName -> {
                       content = "name:" + status.userName
                       Toast.makeText(this@AnimationUseActivity, content, Toast.LENGTH_LONG).show()
                   }
                   R.id.tweetText -> {
                       content = "tweetText:" + status.userName
                       Toast.makeText(this@AnimationUseActivity, content, Toast.LENGTH_LONG).show()
                   }
               }// you have set clickspan .so there should not solve any click event ,just empty
           }
```
# 设置 it item long click
```java
  adapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, view, position ->
            Log.d(TAG, "onItemLongClick: ")
            Toast.makeText(this@ItemClickActivity, "onItemLongClick" + position, Toast.LENGTH_SHORT).show()
            true
        }
```
# 设置 it item child long click
首先需要添加需要点击触发的 childview id 
``` 
 override fun convert(helper: BaseViewHolder, item: Status) {
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName)
    }
```
然后
```java
 adapter.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
            Log.d(TAG, "onItemChildLongClick: ")
            Toast.makeText(this@ItemClickActivity, "onItemChildLongClick" + position, Toast.LENGTH_SHORT).show()
            true
        }
```



# 如何使用它添加动画？

```java
// 一行代码搞定（默认为渐显效果）
  mAnimationAdapter.openLoadAnimation()
```
不喜欢渐显动画可以这样更换
```java
// 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
```
还是没你喜欢的，你可以自定义
```java
// 自定义动画如此轻松
  mAnimationAdapter.openLoadAnimation(BaseAnimation 
                                    { view -> arrayOf(ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f, 1.0f), ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f, 1.0f))
                                    })
```
# 使用它添加头部添加尾部

```java
// add
mQuickAdapter.addHeaderView(getView());
mQuickAdapter.addFooterView(getView());
// remove
removeHeaderView(getView);
removeFooterView(getView);
// or
removeAllHeaderView();
removeAllFooterView();
```
# 使用它加载更多

```java
mQuickAdapter.openLoadMore(PAGE_SIZE, true);
mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            mQuickAdapter.notifyDataChangedAfterLoadMore(false);
                        } else {
                            mQuickAdapter.notifyDataChangedAfterLoadMore(DataServer.getSampleData(PAGE_SIZE), true);
                            mCurrentCounter = mQuickAdapter.getItemCount();
                        }
                    }

                });
            }
        });
```
# 设置自定义加载更多布局
```java
mQuickAdapter.setLoadingView(customView);
```
# 使用分组

```java
class SectionAdapter constructor(layoutResId: Int, sectionHeadResId: Int, data: List<MySection>) : BaseSectionQuickAdapter<MySection, BaseViewHolder>(layoutResId, sectionHeadResId, data) {

    override fun convertHead(helper: BaseViewHolder, item: MySection) {
        helper.setText(R.id.header, item.header)
        helper.setVisible(R.id.more, item.isMore)
        helper.addOnClickListener(R.id.more)
    }


    override fun convert(helper: BaseViewHolder, item: MySection) {
        val video = item.t as Video
        when (helper.layoutPosition % 2) {
            0 -> helper.setImageResource(R.id.iv, R.mipmap.m_img1)
            1 -> helper.setImageResource(R.id.iv, R.mipmap.m_img2)
        }
        helper.setText(R.id.tv, video.name)
    }
}
```
# 如何添加多种类型item？

```java
class MultipleItemQuickAdapter(context: Context, data: List<MultipleItem>) : BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(data) {

    init {
        addItemType(MultipleItem.TEXT, R.layout.item_text_view)
        addItemType(MultipleItem.IMG, R.layout.item_image_view)
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (helper.itemViewType) {
            MultipleItem.TEXT -> helper.setText(R.id.tv, item.content)
            MultipleItem.IMG_TEXT -> when (helper.layoutPosition % 2) {
                0 -> helper.setImageResource(R.id.iv, R.mipmap.animation_img1)
                1 -> helper.setImageResource(R.id.iv, R.mipmap.animation_img2)
            }
        }
    }

}

```
# 使用setEmptyView

```java
mQuickAdapter.setEmptyView(getView());
```
# 使用拖拽与滑动删除

```java
  val listener = object : OnItemDragListener {
            override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Log.d(TAG, "drag start")
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            override fun onItemDragMoving(source: RecyclerView.ViewHolder, from: Int, target: RecyclerView.ViewHolder, to: Int) {
                Log.d(TAG, "move from: " + source.adapterPosition + " to: " + target.adapterPosition)
            }

            override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Log.d(TAG, "drag end")
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.BLACK);
            }
        }

val onItemSwipeListener = object : OnItemSwipeListener {
            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Log.d(TAG, "view swiped start: " + pos)
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.WHITE);
            }

            override fun clearView(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Log.d(TAG, "View reset: " + pos)
                val holder = viewHolder as BaseViewHolder
                //                holder.setTextColor(R.id.tv, Color.BLACK);
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, pos: Int) {
                Log.d(TAG, "View Swiped: " + pos)
            }

            override fun onItemSwipeMoving(canvas: Canvas, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
                canvas.drawColor(ContextCompat.getColor(this@ItemDragAndSwipeUseActivity, R.color.color_light_blue))
                //                canvas.drawText("Just some text", 0, 40, paint);
            }
        }

class ItemDragAdapter(data: List<String>) : BaseItemDraggableAdapter<String, BaseViewHolder>(R.layout.item_draggable_view, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        when (helper.layoutPosition % 3) {
            0 -> helper.setImageResource(R.id.iv_head, R.mipmap.head_img0)
            1 -> helper.setImageResource(R.id.iv_head, R.mipmap.head_img1)
            2 -> helper.setImageResource(R.id.iv_head, R.mipmap.head_img2)
        }
        helper.setText(R.id.tv, item)
    }
}


   mAdapter = ItemDragAdapter(mData)
        mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mAdapter)
        mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRecyclerView)

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        mAdapter.enableSwipeItem()
        mAdapter.setOnItemSwipeListener(onItemSwipeListener)
        mAdapter.enableDragItem(mItemTouchHelper)
        mAdapter.setOnItemDragListener(listener)
        //        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        mRecyclerView.adapter = mAdapter
```

# Expandable Item

```Java
// 如果不想使用继承，可以只实现IExpandable接口
// AbstractExpandableItem只是个帮助类
data class Level0Item(var title: String, var subTitle: String) : AbstractExpandableItem<Level1Item>(), MultiItemEntity {

    override fun getItemType(): Int {
        return ExpandableItemAdapter.TYPE_LEVEL_0
    }

    override fun getLevel(): Int {
        return 0
    }
}

```
in adapter code
```Java

(data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0)
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1)
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv2)
    }


    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {
        when (holder.itemViewType) {
            TYPE_LEVEL_0 -> {
                when (holder.layoutPosition % 3) {
                    0 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img0)
                    1 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img1)
                    2 -> holder.setImageResource(R.id.iv_head, R.mipmap.head_img2)
                }
                val lv0 = item as Level0Item
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle)
                        .setImageResource(R.id.iv, if (lv0.isExpanded()) R.mipmap.arrow_b else R.mipmap.arrow_r)
                holder.itemView.setOnClickListener {
                    val pos = holder.adapterPosition
                    Log.d(TAG, "Level 0 item pos: " + pos)
                    if (lv0.isExpanded()) {
                        collapse(pos)
                    } else {
                        //                            if (pos % 3 == 0) {
                        //                                expandAll(pos, false);
                        //                            } else {
                        expand(pos)
                        //                            }
                    }
                }
            }
            TYPE_LEVEL_1 -> {
                val lv1 = item as Level1Item
                holder.setText(R.id.title, lv1.title)
                        .setText(R.id.sub_title, lv1.subTitle)
                        .setImageResource(R.id.iv, if (lv1.isExpanded()) R.mipmap.arrow_b else R.mipmap.arrow_r)
                holder.itemView.setOnClickListener {
                    val pos = holder.adapterPosition
                    Log.d(TAG, "Level 1 item pos: " + pos)
                    if (lv1.isExpanded()) {
                        collapse(pos, false)
                    } else {
                        expand(pos, false)
                    }
                }
            }
            TYPE_PERSON -> {
                val person = item as Person
                holder.setText(R.id.tv, person.name + " parent pos: " + getParentPosition(person))
                holder.itemView.setOnClickListener {
                    val cp = getParentPosition(person)
                    (data[cp] as Level1Item).removeSubItem(person)
                    data.removeAt(holder.layoutPosition)
                    notifyItemRemoved(holder.layoutPosition)
                }
            }
        }
    }

    companion object {
        private val TAG = ExpandableItemAdapter::class.java.simpleName

        val TYPE_LEVEL_0 = 0
        val TYPE_LEVEL_1 = 1
        val TYPE_PERSON = 2
    }
}
```


Use Custom BaseViewHolder
```Java

// 当使用自定义的BaseViewHolder时，需要重写此函数以创建ViewHolder
protected K createBaseViewHolder(View view) {
    return (K) new BaseViewHolder(view);
}

```

>**持续更新!，所以推荐Star项目**

# 感谢
[JoanZapata / base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)
