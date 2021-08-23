package com.weare.wearecompany.ui.listcontainer

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.MyApplication

class stickHeaderItemDecoration(private val sectionCallback: SectionCallback) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        /* 헤더 */
        val currentHeader: View = sectionCallback.getHeaderLayoutView(parent, topChildPosition) ?: return
        /* View의 레이아웃 설정 */
        fixLayoutSize(
                parent,
                currentHeader,
                        topChild.measuredHeight
            )
        currentHeader.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(MyApplication.instance, "헤더",Toast.LENGTH_SHORT).show()

            }

        })

        val contactPoint = currentHeader.bottom

        val childInContact: View = getChildInContact(parent, contactPoint) ?: return

        val childAdapterPosition = parent.getChildAdapterPosition(childInContact)
        if (childAdapterPosition == -1) {
            return
        }
        if (sectionCallback.isSection(childAdapterPosition)) {
            moveHeader(
                c,
                currentHeader,
                childInContact
            )
            return
            }
        drawHeader(c, currentHeader)
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, nextHeader.top-currentHeader.height.toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    /** * Measures the header view to make sure its size is greater than 0 and will be drawn * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations */
    private fun fixLayoutSize(parent: ViewGroup, view: View, height: Int) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)
        val childWidth: Int = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeight: Int = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidth,childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    interface SectionCallback {
        fun isSection(position: Int): Boolean    //해당 포지션이 고정될 뷰 인지 판단
        fun getHeaderLayoutView(list: RecyclerView, position: Int): View?   //해당 포지션에 해당하는 뷰를 가져온다.
    }
}