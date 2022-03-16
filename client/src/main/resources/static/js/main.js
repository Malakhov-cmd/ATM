import Vue from 'vue'
import VueRouter from "vue-router"

import './plugin/bootstrapPlugin'

import App from "./pages/App.vue";
import Login from "./components/login/Login.vue";

const routes = [
    {path: '/login', component: Login}
]

const router = new VueRouter({
    mode: 'hash',
    routes
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')