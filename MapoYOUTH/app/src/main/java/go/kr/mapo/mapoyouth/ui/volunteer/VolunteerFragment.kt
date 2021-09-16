package go.kr.mapo.mapoyouth.ui.volunteer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import go.kr.mapo.mapoyouth.R
import go.kr.mapo.mapoyouth.databinding.FragmentVolunteerBinding
import go.kr.mapo.mapoyouth.ui.common.ListItemPagerAdapter
import go.kr.mapo.mapoyouth.util.Constants.FLAG_VOLUNTEER
import go.kr.mapo.mapoyouth.util.CustomAttr

class VolunteerFragment : Fragment() {

    lateinit var binding : FragmentVolunteerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_volunteer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val tabList = resources.getStringArray(R.array.volunteer_tab)
            viewPager.apply {
                currentItem = 0
                adapter = ListItemPagerAdapter(FLAG_VOLUNTEER)
            }
            val tabItem = tabs.getChildAt(0) as ViewGroup
            tabs.getTabAt(0)!!.select().also { CustomAttr.changeTabsBold(tabItem, 0, tabs.tabCount) }
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when(position) {
                    0 -> tabList[0]
                    1 -> tabList[1]
                    2 -> tabList[2]
                    3 -> tabList[3]
                    else -> tabList[4]
                }
            }.attach()
        }
    }

}