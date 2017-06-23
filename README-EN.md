# BaseRecyclerViewAdapterHelper-kotlin-demo

# Document


1. A demo of an BaseRecyclerViewAdapterHelper  app using Kotlin
2. This Demo help you how to use kotlin to develop with BaseRecyclerViewAdapterHelper

## [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
## [Java Demo](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/tree/master/app)

## [Demo UI](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/694)



### The sample code
``` stylus

/**
 * 文 件 名: HomeActivity
 * 创 建 人: Allen
 * 创建日期: 2017/6/13 13:59
 * 修改时间：
 * 修改备注：
 */
class HomeActivity : AppCompatActivity() {
    private var mDataList: ArrayList<HomeItem>? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        initData()
        initAdapter()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.rv_list) as RecyclerView
        mRecyclerView!!.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initAdapter() {
        val homeAdapter = HomeAdapter(R.layout.home_item_view, mDataList!!)
        homeAdapter.openLoadAnimation()
        val top = layoutInflater.inflate(R.layout.top_view, mRecyclerView!!.parent as ViewGroup, false)
        homeAdapter.addHeaderView(top)
        homeAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val intent = Intent(this@HomeActivity, ACTIVITY[position])
            startActivity(intent)
        }
        homeAdapter.notifyDataSetChanged()
        mRecyclerView!!.adapter = homeAdapter
    }

    private fun initData() {
        mDataList = ArrayList<HomeItem>()
        for (i in TITLE.indices) {
            val item = HomeItem()
            item.title = (TITLE[i])
            item.activity = (ACTIVITY[i])
            item.imageResource = (IMG[i])
            mDataList!!.add(item)
        }
    }

    /*
    * 伴生对象该伴⽣对象的成员可通过只使⽤类名作为限定符来调⽤：
    *
    * */
    companion object {
        private val ACTIVITY = arrayOf<Class<*>>(AnimationUseActivity::class.java, MultipleItemUseActivity::class.java, HeaderAndFooterUseActivity::class.java, PullToRefreshUseActivity::class.java, SectionUseActivity::class.java, EmptyViewUseActivity::class.java, ItemDragAndSwipeUseActivity::class.java, ItemClickActivity::class.java, ExpandableUseActivity::class.java, DataBindingUseActivity::class.java, UpFetchUseActivity::class.java)
        private val TITLE = arrayOf("Animation", "MultipleItem", "Header/Footer", "PullToRefresh", "Section", "EmptyView", "DragAndSwipe", "ItemClick", "ExpandableItem", "DataBinding", "UpFetchData")
        private val IMG = intArrayOf(R.mipmap.gv_animation, R.mipmap.gv_multipleltem, R.mipmap.gv_header_and_footer, R.mipmap.gv_pulltorefresh, R.mipmap.gv_section, R.mipmap.gv_empty, R.mipmap.gv_drag_and_swipe, R.mipmap.gv_item_click, R.mipmap.gv_expandable, R.mipmap.gv_databinding, R.drawable.gv_up_fetch)
    }

}
```






