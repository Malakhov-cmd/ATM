import Vue from 'vue'
import VueRouter from "vue-router"

import './plugin/bootstrapPlugin'

import App from "./pages/App.vue";
import Login from "./components/login/Login.vue";
import Home from "./components/home/Home.vue";

const routes = [
    {path: '/login', component: Login},
    {path: '/home', component: Home}
]

Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'hash',
    routes
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')