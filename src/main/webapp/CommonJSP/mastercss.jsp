<%@ page import="java.util.ResourceBundle" %>

<%
    ResourceBundle rootnames = ResourceBundle.getBundle("CAMPS.properties.rootnames");
    String cssTheme = "";
    String minlogo = "";
    if (rootnames.containsKey("css")) {
        cssTheme = rootnames.getString("css").trim();
    }
    if (rootnames.containsKey("minlogo")) {
        minlogo = rootnames.getString("minlogo").trim();
    }
%>
<%
    if ("slateskyrootaccess".equals(cssTheme)) {
%>

    <%@ include file="slateskyrootaccess.jsp" %>

<%
    } else if ("whitebluerootaccess".equals(cssTheme)) {
%>

    <%@ include file="whitebluerootaccess.jsp" %>

<%
    } else if ("royaalpurplerootaccess".equals(cssTheme)) {
%>

    <%@ include file="royaalpurplerootaccess.jsp" %>

<%
    } else if ("emeraldtealrootaccess".equals(cssTheme)) {
%>

    <%@ include file="emeraldtealrootaccess.jsp" %>

<%
    } else if ("deepindigorootcss".equals(cssTheme)) {
%>

    <%@ include file="deepindigorootcss.jsp" %>

<%
    } else {
%>

    <%@ include file="slateskyrootaccess.jsp" %>

<%
    }
%>
<style>
/* ==========================================
   RESET & BASE STYLES
========================================== */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

html,
body {
    width: 100%;
    height: 100%;
    font-family: var(--cs-font-style);
    overflow: hidden;
    background-color: var(--cs-body-bg);
    color: var(--cs-body-font);
}

a,
a:hover,
a:active {
    text-decoration: none;
    color: inherit;
}

ul {
    list-style: none;
}

/* ==========================================
   ROOT MAIN LAYOUT GRID STRUCTURE
========================================== */
.cs-main-container-structure {
    width: 100%;
    height: 100%;
    height: 100dvh;
    display: grid;
    grid-template-columns: var(--cs-menu-width) 1fr;
    grid-template-rows: 64px 1fr;
    grid-template-areas:
        "left top"
        "left middle";
    transition: grid-template-columns 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.cs-main-container-structure.sidebar-collapsed {
    grid-template-columns: 72px 1fr;
}

.cs-main-container-structure.sidebar-collapsed .cs-menu-label span:not(.cs-icon-wrapper),
.cs-main-container-structure.sidebar-collapsed .cs-arrow,
.cs-main-container-structure.sidebar-collapsed .cs-left-top .cs-full-logo,
.cs-main-container-structure.sidebar-collapsed .cs-submenu {
    display: none !important;
}

.cs-main-container-structure.sidebar-collapsed .cs-left-top {
    justify-content: center;
    padding: 10px 0;
}

.cs-mini-logo {
    display: none;
}

.cs-main-container-structure.sidebar-collapsed .cs-full-logo {
    display: none !important;
}

.cs-main-container-structure.sidebar-collapsed .cs-mini-logo {
    display: block;
}

.cs-main-container-structure.sidebar-collapsed .cs-sidebar-menu>li>a,
.cs-main-container-structure.sidebar-collapsed .cs-submenu li>a {
    justify-content: center !important;
    padding: 0.75rem 0 !important;
    margin-left: 0 !important;
    font-size: var(--cs-font-la) !important;
    overflow: visible !important;
}

.cs-main-container-structure.sidebar-collapsed .cs-sidebar-menu>li>a .cs-menu-label,
.cs-main-container-structure.sidebar-collapsed .cs-submenu li>a .cs-menu-label {
    justify-content: center !important;
    align-items: center !important;
    gap: 0 !important;
    overflow: visible !important;
    width: auto !important;
    display: flex !important;
}

.cs-main-container-structure.sidebar-collapsed .cs-sidebar-menu>li>a .cs-icon-wrapper,
.cs-main-container-structure.sidebar-collapsed .cs-submenu li>a .cs-icon-wrapper,
.cs-main-container-structure.sidebar-collapsed .cs-icon-wrapper {
    display: inline-flex !important;
    visibility: visible !important;
    opacity: 1 !important;
    width: 24px !important;
    min-width: 24px !important;
    height: 24px !important;
    min-height: 24px !important;
    justify-content: center !important;
    align-items: center !important;
    color: var(--cs-left-side-bar-icon-color) !important;
    overflow: visible !important;
}

.cs-main-container-structure.sidebar-collapsed .cs-sidebar-menu>li>a .cs-icon-wrapper .material-symbols-outlined,
.cs-main-container-structure.sidebar-collapsed .cs-sidebar-menu>li>a .cs-icon-wrapper i,
.cs-main-container-structure.sidebar-collapsed .cs-submenu li>a .cs-icon-wrapper .material-symbols-outlined,
.cs-main-container-structure.sidebar-collapsed .cs-submenu li>a .cs-icon-wrapper i {
    display: inline-flex !important;
    visibility: visible !important;
    opacity: 1 !important;
    color: var(--cs-left-side-bar-icon-color) !important;
    font-size: 24px !important;
    line-height: 1 !important;
    overflow: visible !important;
}

.cs-main-container-structure.sidebar-collapsed li.active>a .cs-icon-wrapper,
.cs-main-container-structure.sidebar-collapsed li.active-path>a .cs-icon-wrapper {
    color: var(--cs-left-side-bar-icon-color) !important;
}

/* ==========================================
   LEFT SIDEBAR STYLING
========================================== */
.cs-container-left {
    grid-area: left;
    width: 100%;
    /* height: 100vh; */
    background-color: var(--cs-left-side-bar-bg);
    display: flex;
    flex-direction: column;
    z-index: 100;
    user-select: none;
}

.cs-container-left .cs-left-top {
    height: 64px;
    padding: 10px 16px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    background-color: var(--cs-bg-left-side-bar-top-bg);
}

.cs-container-left .cs-left-top img {
    max-width: 190px;
    max-height: 42px;
    object-fit: contain;
}

.cs-container-left .cs-left-full {
    width: 100%;
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    padding: 0.6rem 0.5rem;
}

.cs-left-full::-webkit-scrollbar {
    width: 4px;
}

.cs-left-full::-webkit-scrollbar-track {
    background: var(--cs-bg-left-side-bar-top-bg);
}

.cs-left-full::-webkit-scrollbar-thumb {
    background: var(--cs-bg-left-side-bar-top-bg);
    border-radius: 4px;
}

.cs-sidebar-menu,
.cs-submenu {
    width: 100%;
}

.cs-sidebar-menu li {
    margin-bottom: 1rem;
    position: relative;
}

.cs-sidebar-menu>li>a {
    text-decoration: none;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1rem 0.75rem 0.65rem 1rem;
    color: var(--cs-left-side-bar-font1);
    border-radius: 6px;
    font-size: var(--cs-font-xl);
    font-weight: 600;
    transition: all 0.2s ease;
    cursor: pointer;
}

.cs-sidebar-menu>li>a:hover {
    background-color: rgba(255, 255, 255, 0.08);
    text-decoration: none;
}

.cs-sidebar-menu>li>a .cs-icon-wrapper {
    color: var(--cs-left-side-bar-icon-color);
}

.cs-submenu {
    display: none;
    padding-left: 1.25rem;
    margin-top: 0.5rem;
    position: relative;
}

.cs-submenu::before {
    content: '';
    position: absolute;
    left: 1.5rem;
    top: 0.2rem;
    bottom: 0.2rem;
    width: 1px;
    background-color: var(--cs-left-side-bar-tree-line);
}

.cs-submenu li>a {
    text-decoration: none;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.65rem 0.75rem 0.65rem 1.25rem;
    margin-left: 0.75rem;
    color: var(--cs-left-side-bar-font2);
    border-radius: 6px;
    font-size: var(--cs-font-la);
    font-weight: 700;
    transition: all 0.2s ease;
}

.cs-submenu li>a:hover {
    text-decoration: none;
    background-color: rgba(255, 255, 255, 0.05);
    color: var(--cs-left-side-bar-icon-color);
}

.cs-submenu li>a .cs-icon-wrapper {
    color: var(--cs-left-side-bar-icon-color);
    opacity: 0.9;
}

.cs-sidebar-menu li.active>a {
    background-color: var(--cs-left-side-bar-menu-active-bg) !important;
    color: var(--cs-left-side-bar-menu-active-color) !important;
    font-weight: 700 !important;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.cs-sidebar-menu li.active>a .cs-icon-wrapper,
.cs-sidebar-menu li.active>a .cs-icon-wrapper .material-symbols-outlined,
.cs-sidebar-menu li.active>a .cs-icon-wrapper i,
.cs-sidebar-menu li.active>a .cs-arrow {
    color: var(--cs-left-side-bar-icon-color) !important;
}

.cs-sidebar-menu li.active-path>a {
    color: var(--cs-left-side-bar-icon-color);
    font-weight: 600;
    background-color: rgba(56, 189, 248, 0.08);
}

.cs-menu-label {
    display: flex;
    align-items: center;
    gap: 12px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.cs-icon-wrapper {
    width: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: var(--cs-font-me);
    flex-shrink: 0;
}

.cs-icon-wrapper .material-symbols-outlined {
    font-size: 20px;
    line-height: 1;
}

.cs-sidebar-menu .cs-arrow {
    font-size: 0.7rem;
    transition: transform 0.25s cubic-bezier(0.4, 0, 0.2, 1);
    color: var(--cs-left-side-bar-arrow-bg);
}

.cs-sidebar-menu li.open>a>.cs-arrow {
    transform: rotate(90deg);
    color: var(--cs-left-side-bar-icon-color);
}

/* ==========================================
   TOP HEADER STYLING
========================================== */
.cs-container-top {
    grid-area: top;
    width: 100%;
    height: 100%;
    background-color: var(--cs-top-bar-bg);
    border-bottom: 1px solid var(--cs-top-bar-border-bg);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 1.5rem;
    position: relative;
    z-index: 80;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.02);
}

.cs-container-top-left {
    display: flex;
    align-items: center;
    gap: 1.25rem;
}

.cs-container-top-left>i,
.cs-container-top-left>span:not(.cs-hamburger):not(.cs-container-search) {
    display: none !important;
}

.cs-search-trigger,
.cs-search-close {
    display: none;
    cursor: pointer;
    color: var(--cs-top-bar-ham-color);
    font-size: var(--cs-font-me);
}

.cs-hamburger {
    cursor: pointer;
    font-size: var(--cs-font-la);
    color: var(--cs-top-bar-ham-color);
    display: flex;
    align-items: center;
    justify-content: center;
    width: 38px;
    height: 38px;
    border-radius: 8px;
    transition: background 0.2s, color 0.2s;
}

.cs-hamburger:hover {
    background-color: var(--cs-top-bar-ham--hover-bg);
    color: var(--cs-top-bar-ham--hover-color);
}

.cs-container-search {
    display: flex;
    align-items: center;
    gap: 10px;
    background-color: var(--cs-top-bar-search-bg);
    padding: 0.55rem 1rem;
    border-radius: 8px;
    width: 320px;
    border: 1px solid var(--cs-top-bar-border-bg);
    transition: all 0.2s ease;
    position: relative;
}

.cs-container-search:focus-within {
    border-color: var(--cs-left-side-bar-icon-color);
    background-color: var(--cs-top-bar-search-focus-bg);
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
}

.cs-container-search i {
    color: var(--cs-top-bar-search-color);
}

.cs-container-search input {
    border: none;
    outline: none;
    background: transparent;
    width: 100%;
    font-size: var(--cs-font-me);
    color: var(--cs-body-font);
}

.cs-search-palette {
    display: none;
    position: absolute;
    top: calc(100% + 8px);
    left: 0;
    width: 380px;
    max-width: 90vw;
    background: var(--cs-top-bar-bg);
    border: 1px solid var(--cs-top-bar-border-bg);
    border-radius: 10px;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.05);
    z-index: 1000;
    max-height: 350px;
    overflow-y: auto;
    padding: 6px;
}

.cs-search-palette.active {
    display: block;
}

.cs-palette-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 12px;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.15s ease;
}

.cs-palette-item:hover,
.cs-palette-item.selected {
    background-color: var(--cs-top-bar-palette-hover);
}

.cs-palette-item .item-title {
    font-size: var(--cs-fs-sm-alt);
    font-weight: 500;
    color: var(--cs-top-bar-palette-text);
    display: flex;
    align-items: center;
    gap: 10px;
}

.cs-palette-item .item-path {
    font-size: var(--cs-font-me);
    color: var(--cs-body-font);
    background: var(--cs-top-bar-palette-hover);
    padding: 2px 6px;
    border-radius: 4px;
}

.cs-container-right {
    display: flex;
    align-items: center;
    gap: 1.25rem;
    position: relative;
}

.cs-notification-wrapper {
    position: relative;
}

.cs-notification {
    position: relative;
    cursor: pointer;
    color: var(--cs-body-font);
    font-size: var(--cs-fs-lg);
    width: 40px;
    height: 40px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: background 0.2s;
}

.cs-notification:hover {
    background-color: var(--cs-top-bar-palette-hover);
    color: var(--cs-bg-side-bar-top);
}

.cs-notification-badge {
    position: absolute;
    top: 2px;
    right: 2px;
    background-color: #ef4444;
    color: white;
    font-size: var(--cs-font-me);
    font-weight: 700;
    width: 18px;
    height: 18px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 2px solid #e2e8f0;
}

/* ==========================================
   NOTIFICATION PANEL STYLING
========================================== */
.cs-dropdown-panel {
    display: none;
    position: absolute;
    top: calc(100% + 12px);
    right: 0;
    width: 340px;
    max-width: 90vw;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1), 0 8px 10px -6px rgba(0, 0, 0, 0.05);
    border: 1px solid #e2e8f0;
    z-index: 1000;
    overflow: hidden;
}

.cs-dropdown-panel.active {
    display: block;
}

.cs-dropdown-panel .cs-panel-header {
    padding: 12px 16px;
    border-bottom: 1px solid #e2e8f0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 600;
    font-size: 0.95rem;
    color: #0f172a;
    background: #f8fafc;
}

.cs-dropdown-panel .cs-panel-body {
    max-height: 300px;
    overflow-y: auto;
}

.cs-notification-item {
    padding: 12px 16px;
    border-bottom: 1px solid #f1f5f9;
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 12px;
    transition: background 0.15s ease;
    user-select: none;
}

.cs-notification-item:hover {
    background: #f8fafc;
}

.cs-notification-item .notif-text {
    font-size: 0.875rem;
    color: #1e293b !important;
    line-height: 1.4;
    font-weight: 400;
}

.cs-notification-item .notif-time {
    font-size: 0.75rem;
    color: #64748b;
    margin-top: 4px;
}

.cs-notification-item .dismiss-btn {
    color: #94a3b8;
    cursor: pointer;
    padding: 2px 4px;
    font-size: 0.85rem;
    transition: color 0.15s ease;
}

.cs-notification-item .dismiss-btn:hover {
    color: #ef4444;
}

/* ==========================================
   USER PROFILE STYLING
========================================== */
.cs-profile-wrapper {
    position: relative;
}

.cs-profile {
    display: flex;
    align-items: center;
    gap: 10px;
    cursor: pointer;
    padding: 4px 8px;
    border-radius: 8px;
    transition: background 0.2s;
}

.cs-profile:hover {
    background-color: #f1f5f9;
}

.cs-profile-avatar {
    width: 34px;
    height: 34px;
    border-radius: 50%;
    background: linear-gradient(135deg, #2563eb, #38bdf8);
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 700;
    font-size: 0.9rem;
    box-shadow: 0 2px 6px rgba(37, 99, 235, 0.3);
    overflow: hidden;
    flex-shrink: 0;
    border: 2px solid #ffffff;
}

.cs-profile-avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    border-radius: 50%;
}

.cs-profile-details {
    display: flex;
    flex-direction: column;
    min-width: 0;
}

.cs-profile-name,
.cs-profile-role {
    display: block;
    width: 140px;
    overflow: hidden;
    white-space: nowrap;
}

.cs-profile-name-text,
.cs-profile-role-text {
    display: inline-block;
    white-space: nowrap;
}

.cs-profile-name {
    font-size: 0.875rem;
    font-weight: 600;
    color: #1e293b;
}

.cs-profile-role {
    font-size: 0.75rem;
    color: #64748b;
}

.cs-profile:hover .cs-profile-name-text,
.cs-profile:hover .cs-profile-role-text {
    animation: cs-profile-marquee 6s linear infinite;
}

@keyframes cs-profile-marquee {
    0%   { transform: translateX(0); }
    100% { transform: translateX(-100%); }
}

.cs-profile-dropdown {
    display: none;
    position: absolute;
    top: calc(100% + 12px);
    right: 0;
    width: 240px;
    background: #ffffff;
    border-radius: 10px;
    box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
    border: 1px solid #e2e8f0;
    z-index: 1000;
    padding: 6px;
}

.cs-profile-dropdown.active {
    display: block;
}

.cs-profile-user-info {
    padding: 10px 12px;
    display: flex;
    flex-direction: column;
    gap: 6px;
    background: #f8fafc;
    border-radius: 6px;
    margin-bottom: 4px;
}

.cs-profile-user-info-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 10px;
    font-size: 0.75rem;
    line-height: 1.3;
}

.cs-profile-user-info-label {
    color: #64748b;
    font-weight: 500;
    flex-shrink: 0;
}

.cs-profile-user-info-value {
    color: #0f172a;
    font-weight: 600;
    text-align: right;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 150px;
}

.cs-profile-menu-divider {
    height: 1px;
    background: #e2e8f0;
    margin: 4px 4px 6px;
}

.cs-profile-menu-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 8px 12px;
    font-size: 0.875rem;
    color: #334155;
    border-radius: 6px;
    cursor: pointer;
    transition: background 0.15s;
}

.cs-profile-menu-item:hover {
    background-color: #f1f5f9;
    color: #0f172a;
}

.cs-profile-menu-item.danger:hover {
    background-color: #fef2f2;
    color: #ef4444;
}

/* ==========================================
   MAIN CONTENT AREA
========================================== */
.cs-container-full {
    grid-area: middle;
    height: 100%;
    padding: 1.5rem;
    padding-bottom: 2.5rem;
    width: 100% !important;
    min-width: 0 !important;
    max-width: none !important;
    display: flex;
    flex-direction: column;
    align-items: stretch !important;
    overflow-x: hidden;
    overflow-y: auto;
    background-color: var(--cs-bg-main-content);
    margin-bottom: 1rem;
}

.cs-container-full .content {
    width: 100% !important;
    min-width: 0 !important;
    max-width: none !important;
    margin: 0 !important;
    padding: 0 !important;
    box-sizing: border-box !important;
    flex: 1 1 auto;
}

/* ==========================================
   BOTTOM FOOTER STYLING
========================================== */
.cs-container-bottom {
    grid-area: bottom;
    width: 100%;
    height: 100%;
    background-color: var(--cs-bg-top);
    border-top: 1px solid var(--cs-top-bar-border-bg);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 1.5rem;
    font-size: var(--cs-fs-xs-alt);
    color: var(--cs-text-muted);
    z-index: 90;
    
}

/* Mobile backdrop */
.cs-sidebar-backdrop {
    display: none;
    position: fixed;
    inset: 0;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(15, 23, 42, 0.6);
    backdrop-filter: blur(2px);
    z-index: 95;
    opacity: 0;
    transition: opacity 0.3s ease;
}

.cs-sidebar-backdrop.active {
    display: block;
    opacity: 1;
}

/* Search no-results view */
.cs-no-results {
    display: none;
    padding: 1.5rem 1rem;
    text-align: center;
    color: var(--cs-text-muted);
    font-size: var(--cs-fs-sm);
}

/* ==========================================
   RESPONSIVE BREAKPOINTS
========================================== */
@media (max-width: 768px) {
    .cs-main-container-structure {
        grid-template-columns: 1fr;
        grid-template-areas:
            "top"
            "middle";
    }

    .cs-container-left {
        position: fixed;
        left: -280px;
        top: 0;
        bottom: 0;
        height: auto;
        width: 280px;
        transition: left 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .cs-container-left.mobile-open {
        left: 0;
    }

    .cs-container-search {
        display: none;
    }

    .cs-profile-details {
        display: flex;
    }

    .cs-profile-name,
    .cs-profile-role {
        width: 100px;
    }

    .cs-search-trigger {
        display: block;
    }

    .cs-container-search.active {
        display: flex;
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        z-index: 10;
        border-radius: 0;
        background: var(--cs-bg-top);
        padding: 0 1.5rem;
    }

    .cs-search-close.active {
        display: block;
        position: absolute;
        right: 1.5rem;
        z-index: 11;
    }
}

/* ==========================================
   CONTENT AREA - CARDS / PANELS / TABLES / FORMS
========================================== */
.cs-resource-info-bar .breadcrumb {
    background-color: var(--cs-text-menu-item) !important;
}

.content .card {
    border: 1px solid var(--cs-top-bar-border-bg) !important;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08) !important;
    border-radius: 7px !important;
}

.content .card > .header {
    background-color: var(--cs-text-menu-item) !important;
    border-radius: 7px 7px 0 0 !important;
}

.content .card > .header h2 {
    color: var(--cs-bg-top) !important;
}

.content .card > .body {
    background-color: var(--cs-bg-content-panel) !important;
    border-radius: 0 0 7px 7px !important;
}

.content .panel.panel-col-blue {
    border: 1px solid var(--cs-top-bar-border-bg) !important;
    border-radius: 7px !important;
    background: var(--cs-bg-top) !important;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08) !important;
}

.content .panel-title a {
    background-color: var(--cs-text-menu-item) !important;
    color: var(--cs-bg-top) !important;
}

.content .panel-body {
    background-color: var(--cs-bg-content-panel) !important;
    border-radius: 0 0 7px 7px !important;
}

.panel-body table {
    
    background-color: var(--cs-bg-top) !important; 
    border-collapse: collapse !important;
    border-radius: 5px;
    overflow-x: auto !important;
    overflow-y: hidden !important;
    -webkit-overflow-scrolling: touch;
}

.panel-body table th {
    background-color: var(--cs-bg-content-table-th) !important;
    color: var(--cs-bg-top) !important;
    border: 1px solid var(--cs-border-content-table-th) !important;
    padding: 6px 3px;
}

.panel-body table td {
    border: 1px solid var(--cs-border-content-table-td) !important;
    color: var(--cs-body-font) !important;
    padding: 3px 1px;
}

.panel-body table input,
.panel-body table select,
.panel-body table textarea {
    
    max-width: 100% !important;
    box-sizing: border-box !important;
}

.panel-body input[type="text"],
.panel-body select {
    border: 1px solid var(--cs-text-side-bar-sub) !important;
    border-radius: 4px !important;
    color: var(--cs-body-font) !important;
    padding: 6px 3px;
}

.panel-body input[type="text"]:focus,
.panel-body select:focus {
    border-color: var(--cs-text-menu-item) !important;
    outline: none !important;
    padding: 6px 3px;
}

.btn.bg-orange {
    background-color: var(--cs-bg-btn-orange) !important;
    color: var(--cs-bg-top) !important;
    border-radius: 4px;
    border: none;
}
</style>