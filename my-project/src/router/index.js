import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/login'
import Home from '@/components/home'
import Information from '@/page/bespeak/information'
import Networkmore from '@/page/bespeak/networkmore'
import meetingRoom from '@/page/bespeak/meetingRoom'
import attendees from '@/page/bespeak/attendees'
import org from '@/page/bespeak/org'
import temporary from '@/page/bespeak/temporary'
import conditions from '@/page/bespeak/conditions'
import details from '@/page/meetingList/details'
import roomDetails from '@/page/meetingList/roomDetails'
import roomDetailsList from '@/page/meetingList/roomDetailsList'
import usersDetailsList from '@/page/meetingList/usersDetailsList'
import controll from '@/page/meetingList/controll/index'
import addMeetingRoom from '@/page/meetingList/controll/more/addMeetingRoom'
import contacts from '@/page/meetingList/controll/more/contacts'
import dial from '@/page/meetingList/controll/more/dial'
import layout from '@/page/meetingList/controll/more/layout'
import rolling from '@/page/meetingList/controll/more/rolling'
import about from '@/page/my/about'
import imgUpdate from '@/page/my/imgUpdate'
import introduce from '@/page/my/introduce'
import help from '@/page/my/help'
import agreement from '@/page/my/agreement'
Vue.use(Router)
export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
    },{
      path: '/bespeak/information',
      name: 'information',
      component: Information,
    },{
      path: '/information/networkmore',
      name: 'networkmore',
      component: Networkmore,
    },{
      path: '/information/meetingRoom',
      name: 'meetingRoom',
      component: meetingRoom,
    },{
      path: '/information/attendees',
      name: 'attendees',
      component: attendees,
    },{
      path: '/information/org',
      name: 'Org',
      component: org,
    },{
      path: '/information/conditions',
      name: 'conditions',
      component: conditions,
    },
    {
      path: '/information/temporary',
      name: 'temporary',
      component: temporary,
    },
    {
      path: '/meetingList/details',
      name: 'details',
      component: details
    },
    {
      path: '/meetingList/roomDetails',
      name: 'roomDetails',
      component: roomDetails
    },{
      path: '/meetingList/roomDetailsList',
      name: 'roomDetailsList',
      component: roomDetailsList
    },
    {
      path: '/meetingList/usersDetailsList',
      name: 'usersDetailsList',
      component: usersDetailsList
    },
    {
      path: '/meetingList/controll',
      name: 'controll',
      component: controll
    },
    {
      path: '/controll/addMeetingRoom',
      name: 'addMeetingRoom',
      component: addMeetingRoom
    },{
      path: '/controll/contacts',
      name: 'contacts',
      component: contacts
    },
    {
      path: '/controll/dial',
      name: 'dial',
      component: dial
    },{
      path: '/controll/layout',
      name: 'layout',
      component: layout
    },{
      path: '/controll/rolling',
      name: 'rolling',
      component: rolling
    },
    {
      path: '/controll/addMeetingRoom',
      name: 'addMeetingRoom',
      component: addMeetingRoom
    },
    {
      path: '/my/about',
      name: 'about',
      component: about
    },{
      path: '/my/imgUpdate',
      name: 'imgUpdate',
      component: imgUpdate
    },
    {
      path: '/my/introduce',
      name: 'introduce',
      component: introduce
    },
    {
      path: '/my/help',
      name: 'help',
      component: help
    },
    {
      path: '/my/agreement',
      name: 'agreement',
      component: agreement
    }
  ]
})
