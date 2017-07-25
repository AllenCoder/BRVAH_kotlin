# This is the Kotlin version of BaseRecyclerViewAdapterHelper using Demo

# [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

# [中文参考文档](https://github.com/AllenCoder/BRVAH_kotlin/blob/master/README-CN.md)

A powerful and flexible RecyclerViewAdapter is welcome to use. (Like you can Star )

# What can it do?（[Download apk](https://fir.im/s91g)）
- **Optimize the Adapter code (70% less code)**
- **Add click items, click events, and click events for item controls**
- **Add a loaded animation (one line of code to easily switch between 5 default animations)**
- **Add head, tail, drop down, pull up load (feel back to ListView era)**
- **Set the custom to load more layouts**
- **Add grouping (mind grouping header)**
- **Customize different item types (simple configuration, no need to rewrite additional methods)**
- **Set the empty layout (than Listview's setEmptyView also easy to use!)**
- **Add a drag item**


# How do you use it?
First add the repositories in build.gradle::
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Then add in the dependencies:
```
	dependencies {
	        compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.21'
	}
```

# How do you use it to create an adapter?

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
# Set the item click to add a click event that adds multiple controls to multiple controls
##  Set it item child click
First you need to add the childview id that you want to click on 
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
# Set  item long click
```java
  adapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, view, position ->
            Log.d(TAG, "onItemLongClick: ")
            Toast.makeText(this@ItemClickActivity, "onItemLongClick" + position, Toast.LENGTH_SHORT).show()
            true
        }
```
# Set  item child long click
First you need to add the childview id that you want to click on
``` 
 override fun convert(helper: BaseViewHolder, item: Status) {
        helper.addOnClickListener(R.id.img).addOnClickListener(R.id.tweetName)
    }
```
then
```java
 adapter.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
            Log.d(TAG, "onItemChildLongClick: ")
            Toast.makeText(this@ItemClickActivity, "onItemChildLongClick" + position, Toast.LENGTH_SHORT).show()
            true
        }
```



# How do I use it to add animations?

```java
// line of code to get (default for the fade effect) 
  mAnimationAdapter.openLoadAnimation()
```
Do not like the fade animation can be replaced
```java
// default to 5 ways (fade, zoom, bottom to top, left to right, right to left) 
mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
```
Or no you like, you can customize it
```java
// custom animation so easy 
  mAnimationAdapter.openLoadAnimation(BaseAnimation 
                                    { view -> arrayOf(ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f, 1.0f), ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f, 1.0f))
                                    })
```
# Use it to add the head to add the tail

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
# Use it to load more

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
# Set the custom to load more layouts
```java
mQuickAdapter.setLoadingView(customView);
```
# Use grouping

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
# How do I add multiple types of items?

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
# Use setEmptyView

```java
mQuickAdapter.setEmptyView(getView());
```
# Use drag and drop to delete

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
// if you do not want to use inheritance, you can just implement the IExpandable interface 
// AbstractExpandableItem is just a help class 
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

// When using a custom BaseViewHolder, you need to override this function to create a ViewHolder 
protected K createBaseViewHolder(View view) {
    return (K) new BaseViewHolder(view);
}

```

>**Continue to update !, so recommend Star project**

# thank
[JoanZapata / base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)
