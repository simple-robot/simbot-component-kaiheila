"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[12],{5162:(e,t,a)=>{a.d(t,{Z:()=>i});var n=a(7294),o=a(6010);const r={tabItem:"tabItem_Ymn6"};function i(e){let{children:t,hidden:a,className:i}=e;return n.createElement("div",{role:"tabpanel",className:(0,o.Z)(r.tabItem,i),hidden:a},t)}},4866:(e,t,a)=>{a.d(t,{Z:()=>x});var n=a(7462),o=a(7294),r=a(6010),i=a(2466),l=a(6550),s=a(1980),u=a(7392),c=a(12);function p(e){return function(e){return o.Children.map(e,(e=>{if(!e||(0,o.isValidElement)(e)&&function(e){const{props:t}=e;return!!t&&"object"==typeof t&&"value"in t}(e))return e;throw new Error(`Docusaurus error: Bad <Tabs> child <${"string"==typeof e.type?e.type:e.type.name}>: all children of the <Tabs> component should be <TabItem>, and every <TabItem> should have a unique "value" prop.`)}))?.filter(Boolean)??[]}(e).map((e=>{let{props:{value:t,label:a,attributes:n,default:o}}=e;return{value:t,label:a,attributes:n,default:o}}))}function d(e){const{values:t,children:a}=e;return(0,o.useMemo)((()=>{const e=t??p(a);return function(e){const t=(0,u.l)(e,((e,t)=>e.value===t.value));if(t.length>0)throw new Error(`Docusaurus error: Duplicate values "${t.map((e=>e.value)).join(", ")}" found in <Tabs>. Every value needs to be unique.`)}(e),e}),[t,a])}function m(e){let{value:t,tabValues:a}=e;return a.some((e=>e.value===t))}function k(e){let{queryString:t=!1,groupId:a}=e;const n=(0,l.k6)(),r=function(e){let{queryString:t=!1,groupId:a}=e;if("string"==typeof t)return t;if(!1===t)return null;if(!0===t&&!a)throw new Error('Docusaurus error: The <Tabs> component groupId prop is required if queryString=true, because this value is used as the search param name. You can also provide an explicit value such as queryString="my-search-param".');return a??null}({queryString:t,groupId:a});return[(0,s._X)(r),(0,o.useCallback)((e=>{if(!r)return;const t=new URLSearchParams(n.location.search);t.set(r,e),n.replace({...n.location,search:t.toString()})}),[r,n])]}function b(e){const{defaultValue:t,queryString:a=!1,groupId:n}=e,r=d(e),[i,l]=(0,o.useState)((()=>function(e){let{defaultValue:t,tabValues:a}=e;if(0===a.length)throw new Error("Docusaurus error: the <Tabs> component requires at least one <TabItem> children component");if(t){if(!m({value:t,tabValues:a}))throw new Error(`Docusaurus error: The <Tabs> has a defaultValue "${t}" but none of its children has the corresponding value. Available values are: ${a.map((e=>e.value)).join(", ")}. If you intend to show no default tab, use defaultValue={null} instead.`);return t}const n=a.find((e=>e.default))??a[0];if(!n)throw new Error("Unexpected error: 0 tabValues");return n.value}({defaultValue:t,tabValues:r}))),[s,u]=k({queryString:a,groupId:n}),[p,b]=function(e){let{groupId:t}=e;const a=function(e){return e?`docusaurus.tab.${e}`:null}(t),[n,r]=(0,c.Nk)(a);return[n,(0,o.useCallback)((e=>{a&&r.set(e)}),[a,r])]}({groupId:n}),f=(()=>{const e=s??p;return m({value:e,tabValues:r})?e:null})();(0,o.useLayoutEffect)((()=>{f&&l(f)}),[f]);return{selectedValue:i,selectValue:(0,o.useCallback)((e=>{if(!m({value:e,tabValues:r}))throw new Error(`Can't select invalid tab value=${e}`);l(e),u(e),b(e)}),[u,b,r]),tabValues:r}}var f=a(2389);const v={tabList:"tabList__CuJ",tabItem:"tabItem_LNqP"};function h(e){let{className:t,block:a,selectedValue:l,selectValue:s,tabValues:u}=e;const c=[],{blockElementScrollPositionUntilNextRender:p}=(0,i.o5)(),d=e=>{const t=e.currentTarget,a=c.indexOf(t),n=u[a].value;n!==l&&(p(t),s(n))},m=e=>{let t=null;switch(e.key){case"Enter":d(e);break;case"ArrowRight":{const a=c.indexOf(e.currentTarget)+1;t=c[a]??c[0];break}case"ArrowLeft":{const a=c.indexOf(e.currentTarget)-1;t=c[a]??c[c.length-1];break}}t?.focus()};return o.createElement("ul",{role:"tablist","aria-orientation":"horizontal",className:(0,r.Z)("tabs",{"tabs--block":a},t)},u.map((e=>{let{value:t,label:a,attributes:i}=e;return o.createElement("li",(0,n.Z)({role:"tab",tabIndex:l===t?0:-1,"aria-selected":l===t,key:t,ref:e=>c.push(e),onKeyDown:m,onClick:d},i,{className:(0,r.Z)("tabs__item",v.tabItem,i?.className,{"tabs__item--active":l===t})}),a??t)})))}function g(e){let{lazy:t,children:a,selectedValue:n}=e;const r=(Array.isArray(a)?a:[a]).filter(Boolean);if(t){const e=r.find((e=>e.props.value===n));return e?(0,o.cloneElement)(e,{className:"margin-top--md"}):null}return o.createElement("div",{className:"margin-top--md"},r.map(((e,t)=>(0,o.cloneElement)(e,{key:t,hidden:e.props.value!==n}))))}function I(e){const t=b(e);return o.createElement("div",{className:(0,r.Z)("tabs-container",v.tabList)},o.createElement(h,(0,n.Z)({},e,t)),o.createElement(g,(0,n.Z)({},e,t)))}function x(e){const t=(0,f.Z)();return o.createElement(I,(0,n.Z)({key:String(t)},e))}},3825:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>d,contentTitle:()=>c,default:()=>f,frontMatter:()=>u,metadata:()=>p,toc:()=>m});var n=a(7462),o=(a(7294),a(3905)),r=a(4866),i=a(5162),l=a(614),s=a(2965);const u={title:"\u4f7f\u7528API",sidebar_position:1},c=void 0,p={unversionedId:"quick-start/api",id:"quick-start/api",title:"\u4f7f\u7528API",description:"API\u6a21\u5757\u662f\u72ec\u7acb\u7684\uff0c\u4f60\u53ef\u4ee5\u5355\u72ec\u4f7f\u7528\u5b83\u4f5c\u4e3a KOOK API \u7684\u5c01\u88c5\u5e93\u3002",source:"@site/docs/quick-start/api.md",sourceDirName:"quick-start",slug:"/quick-start/api",permalink:"/simbot-component-kook/docs/next/quick-start/api",draft:!1,editUrl:"https://github.com/simple-robot/simbot-component-kook/tree/dev/main/website/docs/quick-start/api.md",tags:[],version:"current",lastUpdatedAt:1693844445,formattedLastUpdatedAt:"2023\u5e749\u67084\u65e5",sidebarPosition:1,frontMatter:{title:"\u4f7f\u7528API",sidebar_position:1},sidebar:"tutorialSidebar",previous:{title:"\u5feb\u901f\u5f00\u59cb",permalink:"/simbot-component-kook/docs/next/quick-start/"},next:{title:"\u4f7f\u7528\u6807\u51c6\u5e93",permalink:"/simbot-component-kook/docs/next/quick-start/stdlib"}},d={},m=[{value:"\u5b89\u88c5",id:"\u5b89\u88c5",level:2},{value:"\u4f7f\u7528",id:"\u4f7f\u7528",level:2},{value:"\u83b7\u53d6\u7528\u6237\u9891\u9053\u670d\u52a1\u5668\u5217\u8868",id:"\u83b7\u53d6\u7528\u6237\u9891\u9053\u670d\u52a1\u5668\u5217\u8868",level:3}],k={toc:m},b="wrapper";function f(e){let{components:t,...a}=e;return(0,o.kt)(b,(0,n.Z)({},k,a,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"API\u6a21\u5757\u662f\u72ec\u7acb\u7684\uff0c\u4f60\u53ef\u4ee5\u5355\u72ec\u4f7f\u7528\u5b83\u4f5c\u4e3a ",(0,o.kt)("a",{parentName:"p",href:"https://developer.kookapp.cn/doc/"},"KOOK API")," \u7684\u5c01\u88c5\u5e93\u3002"),(0,o.kt)("admonition",{title:"\u5168\u53d8\u4e86",type:"danger"},(0,o.kt)("p",{parentName:"admonition"},"API\u6a21\u5757\u5728 ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/simple-robot/simbot-component-kook/issues/82"},(0,o.kt)("strong",{parentName:"a"},"#82"))," \u7684\u8fc7\u7a0b\u4e2d\u4f1a",(0,o.kt)("strong",{parentName:"p"},"\u5927\u6539"),"\uff0c\n\u5305\u62ec\u53d8\u66f4API\u63a5\u53e3\u7684\u7ed3\u679c\u5b9e\u73b0\u3001\u4e8b\u4ef6\u7c7b\u578b\u7684\u5b9e\u73b0\u7b49\uff0c\u4ee5\u53ca\u53d8\u66f4\u6240\u6709API\u7684\u547d\u540d\u4e3a ",(0,o.kt)("inlineCode",{parentName:"p"},"GetXxxListApi")," \u98ce\u683c\u7b49\u3002")),(0,o.kt)("h2",{id:"\u5b89\u88c5"},"\u5b89\u88c5"),(0,o.kt)(r.Z,{groupId:"use-dependency",mdxType:"Tabs"},(0,o.kt)(i.Z,{value:"Gradle Kotlin DSL",mdxType:"TabItem"},(0,o.kt)(l.Z,{language:"kotlin",mdxType:"CodeBlock"},`\n// \u4e0d\u8981\u5fd8\u8bb0\u4f7f\u7528 Gradle \u7684 kotlin \u63d2\u4ef6\u6765\u5141\u8bb8\u81ea\u52a8\u9009\u62e9\u5bf9\u5e94\u5e73\u53f0\uff0c\u6bd4\u5982JVM\u6216JS\u7b49\u3002\nimplementation("love.forte.simbot.component:simbot-component-kook-api:${s.i}") // \u7248\u672c\u53c2\u8003\u4e0b\u6587\u6240\u8ff0\u7684 Releases\n`.trim())),(0,o.kt)(i.Z,{value:"Gradle Groovy",mdxType:"TabItem"},(0,o.kt)(l.Z,{language:"gradle",mdxType:"CodeBlock"},`\n// \u4e0d\u8981\u5fd8\u8bb0\u4f7f\u7528 Gradle \u7684 kotlin \u63d2\u4ef6\u6765\u5141\u8bb8\u81ea\u52a8\u9009\u62e9\u5bf9\u5e94\u5e73\u53f0\uff0c\u6bd4\u5982JVM\u6216JS\u7b49\u3002\nimplementation 'love.forte.simbot.component:simbot-component-kook-api:${s.i}' // \u7248\u672c\u53c2\u8003\u4e0b\u6587\u6240\u8ff0\u7684 Releases\n`.trim())),(0,o.kt)(i.Z,{value:"Maven",mdxType:"TabItem"},(0,o.kt)(l.Z,{language:"xml",mdxType:"CodeBlock"},`\n<dependency>\n    <groupId>love.forte.simbot.component</groupId>\n    \x3c!-- \u5728Maven\u4e2d\u4f7f\u7528 '-jvm' \u540e\u7f00\u6765\u9009\u62e9\u4f7f\u7528JVM\u5e73\u53f0\u5e93 --\x3e\n    <artifactId>simbot-component-kook-api</artifactId>\n    \x3c!-- \u7248\u672c\u53c2\u8003\u4e0b\u6587\u6240\u8ff0\u7684 Releases --\x3e\n    <version>${s.i}</version>\n</dependency>\n`.trim()))),(0,o.kt)("admonition",{title:"\u7248\u672c\u53c2\u8003",type:"info"},(0,o.kt)("p",{parentName:"admonition"},"\u7248\u672c\u53ef\u524d\u5f80 ",(0,o.kt)("a",{parentName:"p",href:"https://github.com/simple-robot/simbot-component-kook/releases"},(0,o.kt)("strong",{parentName:"a"},"Releases"))," \u67e5\u770b\u5e76\u9009\u62e9\u3002")),(0,o.kt)("h2",{id:"\u4f7f\u7528"},"\u4f7f\u7528"),(0,o.kt)("admonition",{title:"\u592a\u591a\u4e86",type:"tip"},(0,o.kt)("p",{parentName:"admonition"},"\u6211\u4eec\u4e0d\u4f1a\u5728\u6b64\u5904\u4e00\u4e00\u5217\u4e3e\u6240\u6709\u7684API\u505a\u6f14\u793a\uff0c\u8fd9\u4e0d\u592a\u73b0\u5b9e\u3002\n\u6240\u6709\u7684API\u90fd\u5728\u5305\u8def\u5f84 ",(0,o.kt)("inlineCode",{parentName:"p"},"love.forte.simbot.kook.api")," \u4e0b\uff0c\u4f60\u53ef\u4ee5\u901a\u8fc7 ",(0,o.kt)("a",{parentName:"p",href:"https://docs.simbot.forte.love/"},"API\u6587\u6863")," \u6216\u67e5\u9605\u6e90\u7801\u7684\u65b9\u5f0f\u6765\u5bfb\u627e\u4f60\u6240\u9700\u8981\u7684API\u3002"),(0,o.kt)("p",{parentName:"admonition"},"API\u5305\u88c5\u7c7b\u7684\u547d\u540d\u4e5f\u5b58\u5728\u4e00\u5b9a\u7684\u89c4\u5f8b\uff0c\u6bd4\u5982\u4e00\u4e2a ",(0,o.kt)("inlineCode",{parentName:"p"},"\u83b7\u53d6\u67d0\u5217\u8868")," \u7684API\u901a\u5e38\u4f1a\u88ab\u547d\u540d\u4e3a ",(0,o.kt)("inlineCode",{parentName:"p"},"XxxListRequest"),"\u3002"),(0,o.kt)("p",{parentName:"admonition"},"\u4e0b\u6587\u4f1a\u9009\u62e9\u4e00\u5c0f\u90e8\u5206API\u6765\u505a\u793a\u4f8b\u3002")),(0,o.kt)("h3",{id:"\u83b7\u53d6\u7528\u6237\u9891\u9053\u670d\u52a1\u5668\u5217\u8868"},"\u83b7\u53d6\u7528\u6237\u9891\u9053\u670d\u52a1\u5668\u5217\u8868"),(0,o.kt)("p",null,"\u4ee5 ",(0,o.kt)("a",{parentName:"p",href:"https://developer.kookapp.cn/doc/http/guild#%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E7%9A%84%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%88%97%E8%A1%A8"},"\u83b7\u53d6\u5f53\u524d\u7528\u6237(BOT)\u52a0\u5165\u7684\u670d\u52a1\u5668\u5217\u8868")," \u4e3a\u4f8b\u3002"),(0,o.kt)(r.Z,{groupId:"code",mdxType:"Tabs"},(0,o.kt)(i.Z,{value:"Kotlin",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"// \u7528\u4e8e\u8bf7\u6c42\u7684 Ktor HttpClient\uff0c\u5982\u6709\u5fc5\u8981\u5219\u9700\u8981\u81ea\u884c\u5f15\u5165\u5e76\u9009\u62e9\u9700\u8981\u4f7f\u7528\u7684\u5f15\u64ce\u3002\n// \u53c2\u8003\uff1ahttps://ktor.io/docs/http-client-engines.html\nval client = HttpClient(Java) {\n    // config...\n}\n\n// \u9274\u6743\u4fe1\u606f\n// 'Bot' \u540e\u9762\u8ddf\u7684\u662fbot\u7684token\uff0c\u53c2\u8003 https://developer.kookapp.cn/doc/reference\nval authorization = \"Bot xxxxxxxxxx\"\n\n// \u6784\u5efa\u8981\u8bf7\u6c42\u7684API\uff0c\u5927\u90e8\u5206API\u90fd\u6709\u4e00\u4e9b\u53ef\u9009\u6216\u5fc5\u987b\u7684\u53c2\u6570\u3002\nval api = GuildListRequest.create()\n\n// request data \u4f1a\u68c0\u6d4bresult\uff0c\u7136\u540e\u5c06\u771f\u6b63\u7684data\u7ed3\u679c\u8fd4\u56de (\u6216\u5728\u9a8c\u8bc1\u5931\u8d25\u7684\u60c5\u51b5\u4e0b\u629b\u51fa\u5f02\u5e38)\nval guildListData = api.requestData(client, authorization)\n\nguildListData.items.forEach { ... }\n// \u4e5f\u53ef\u4ee5\u76f4\u63a5 guildListData.forEach { ... }\n"))),(0,o.kt)(i.Z,{value:"Java",mdxType:"TabItem"},(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-java"},"// \u5728Java\u4e2d\u6784\u5efa\u6216\u83b7\u53d6\u4e00\u4e2a Ktor \u7684 HttpClient\u3002\n// \u7528\u4e8e\u8bf7\u6c42\u7684 Ktor HttpClient\uff0c\u5982\u6709\u5fc5\u8981\u5219\u9700\u8981\u81ea\u884c\u5f15\u5165\u5e76\u9009\u62e9\u9700\u8981\u4f7f\u7528\u7684\u5f15\u64ce\u3002\n// \u53c2\u8003\uff1ahttps://ktor.io/docs/http-client-engines.html\n// \u6b64\u5904\u4ee5 ktor-java \u5f15\u64ce\u4e3a\u4f8b\u3002\nvar client = HttpClientKt.HttpClient(Java.INSTANCE, config -> {\n    // config...   \n    return Unit.INSTANCE;\n});\n\n// \u9274\u6743\u4fe1\u606f\n// 'Bot' \u540e\u9762\u8ddf\u7684\u662fbot\u7684token\uff0c\u53c2\u8003 https://developer.kookapp.cn/doc/reference\nvar authorization = \"Bot xxxxxxxxxx\";\n\n// \u6784\u5efa\u8981\u8bf7\u6c42\u7684API\uff0c\u5927\u90e8\u5206API\u90fd\u6709\u4e00\u4e9b\u53ef\u9009\u6216\u5fc5\u987b\u7684\u53c2\u6570\u3002\nvar api = GuildListRequest.create();\n\n// requestDataBlocking \u4f1a\u68c0\u6d4bresult\uff0c\u7136\u540e\u5c06\u771f\u6b63\u7684data\u7ed3\u679c\u8fd4\u56de (\u6216\u5728\u9a8c\u8bc1\u5931\u8d25\u7684\u60c5\u51b5\u4e0b\u629b\u51fa\u5f02\u5e38)\nvar guildListData = api.requestDataBlocking(client, authorization);\n\nfor (var guild : guildListData) {\n    System.out.println(guild);\n}\n")))))}f.isMDXComponent=!0},2965:e=>{e.exports=JSON.parse('{"i":"3.2.0.0-alpha.8-dev.4"}')}}]);