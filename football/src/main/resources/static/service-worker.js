if(!self.define){let e,s={};const n=(n,i)=>(n=new URL(n+".js",i).href,s[n]||new Promise((s=>{if("document"in self){const e=document.createElement("script");e.src=n,e.onload=s,document.head.appendChild(e)}else e=n,importScripts(n),s()})).then((()=>{let e=s[n];if(!e)throw new Error(`Module ${n} didn’t register its module`);return e})));self.define=(i,o)=>{const r=e||("document"in self?document.currentScript.src:"")||location.href;if(s[r])return;let l={};const t=e=>n(e,r),f={module:{uri:r},exports:l,require:t};s[r]=Promise.all(i.map((e=>f[e]||t(e)))).then((e=>(o(...e),l)))}}define(["./workbox-79ffe3e0"],(function(e){"use strict";e.setCacheNameDetails({prefix:"frontend"}),self.addEventListener("message",(e=>{e.data&&"SKIP_WAITING"===e.data.type&&self.skipWaiting()})),e.precacheAndRoute([{url:"/css/852.9f071c2e.css",revision:null},{url:"/css/app.135215a8.css",revision:null},{url:"/css/chunk-vendors.ab718122.css",revision:null},{url:"/fonts/materialdesignicons-webfont.21f691f0.ttf",revision:null},{url:"/fonts/materialdesignicons-webfont.54b0f60d.woff2",revision:null},{url:"/fonts/materialdesignicons-webfont.5d875350.eot",revision:null},{url:"/fonts/materialdesignicons-webfont.d671cbf6.woff",revision:null},{url:"/index.html",revision:"4adbf6709bd719b02386465f28c8f3c6"},{url:"/js/852.83f56be2.js",revision:null},{url:"/js/app.cb3dda23.js",revision:null},{url:"/js/chunk-vendors.f8a432f8.js",revision:null},{url:"/js/webfontloader.3c03961e.js",revision:null},{url:"/manifest.json",revision:"4b14c64efaf846819b9a229b4193c8b7"},{url:"/robots.txt",revision:"b6216d61c03e6ce0c9aea6ca7808f7ca"}],{})}));
//# sourceMappingURL=service-worker.js.map
