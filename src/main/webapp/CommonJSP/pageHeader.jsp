<%@ include file="masterlink.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>CampusStack</title>
    <%
        String host_string = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        String hostname = host_string;
        String menu_json = "[]";
        try {
            if(session.getAttribute("menu_json_cache") != null){
                menu_json = session.getAttribute("menu_json_cache").toString();
            } else {
                menu menuobj = new menu();
                menu_json = menuobj.menu_generator_json(session.getAttribute("roles").toString(), hostname);
                session.setAttribute("menu_json_cache", menu_json);
            }
        } catch(Exception menuEx) {
            menu_json = "[]";
        }
    %>
</head>
<script>
    const menuJson = <%= menu_json %>;
</script>

<body class="theme-blue">
    <div class="page-container only-print">
        Page <span class="page"></span>
    </div>
    <!-- Page Loader -->
    <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-red">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p style="color: #000;">Please wait...</p>
        </div>
    </div>
    <div class="overlay"></div>

    <%
		String resource_id = "";
		ResourceBundle rb = ResourceBundle.getBundle("CAMPS.properties.software");
		PageHeader ph = new PageHeader();
		try{
			if(session.getAttribute("roles") == null){
				response.sendRedirect(host_string + "CommonJSP/signin.jsp" );
			}
			if (request.getParameter("am_c") != null) {
				String am_c = request.getParameter("am_c");
				switch (request.getParameter("am_c")){
				case "1":
					out.print("<script>showNotification('alert-success', 'Process Completed...','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
					break;
				case "2":
					out.print("<script>showNotification('alert-danger', 'Insufficient Role...','top', 'right',  '', '',false,'glyphicon glyphicon-cd glyphicon-spin');</script>");
					break;
				case "error":
					out.print("<script> showNotification('alert-danger', '"+request.getParameter("error")+"','top', 'right',  '', '',false,'glyphicon glyphicon-thumbs-down');</script>");
					break;
				default:
					out.print("<script> showNotification('alert-danger', '<b title=\""+request.getParameter("am_c")+"\">Some Thing went Wrong!!!<b>','top', 'right',  '', '',false,'glyphicon glyphicon-thumbs-down');</script>");
					break;
			}
		}
	%>
    <div class="cs-sidebar-backdrop" id="sidebarBackdrop"></div>
    
    <div class="cs-main-container-structure" id="mainContainer">
        <!-- ==========================================
        LEFT SIDEBAR (MULTI-LEVEL NESTED ACCORDION)
        ========================================== -->
        <aside class="cs-container-left" id="sidebar">
            <div class="cs-left-top">
                <img class="cs-full-logo" src='<%=ph.getLogoImageURL()%> ' alt='Logo' onerror="this.style.display='none'; this.nextElementSibling.style.display='block';">
                <span style="display:none; color:white; font-weight:700; font-size:1.1rem; letter-spacing:0.5px;">CAMPUSSTACK</span>
                <img class="cs-mini-logo" src="<%=minlogo%>" alt="Mini Logo" style="width:40px; height:40px;">            
            </div>
                <div class="cs-left-full">
                    <div class="cs-no-results" id="noResultsMsg">
                        <i class="fa-solid fa-magnifying-glass" style="margin-bottom:8px; font-size:1.2rem;"></i><br> No matching menu options found
                    </div>
                    <ul class="cs-sidebar-menu" id="sidebarMenu">
                    </ul>
                </div>
        </aside>

        <!-- ==========================================
        TOP HEADER BAR
        ========================================== -->
            <header class="cs-container-top">
                <div class="cs-container-top-left">
                    <div class="cs-hamburger" id="hamburgerBtn" title="Toggle Navigation">
                        <i class="fa-solid fa-bars" id="hamburgerIcon"></i>
                    </div>
                    <div class="cs-search-trigger" id="mobileSearchOpen">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </div>
                    <div class="cs-search-close" id="mobileSearchClose">
                        <i class="fa-solid fa-xmark"></i>
                    </div>
                    <div class="cs-container-search" id="searchContainer">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        <input type="text" id="menuSearchInput" placeholder="Search menu items... (Command Palette)" autocomplete="off">
                        <div class="cs-search-palette" id="searchPalette"></div>
                    </div>
                </div>

                <div class="cs-container-right">

                    <%String notificationJson = ph.getUserNotification(session.getAttribute("uType").toString(),session.getAttribute("ss_id").toString());%>
                    <script>
                        var userNotifications = <%=notificationJson%>;
                    </script>
                    <div class="cs-notification-wrapper">
                        <div class="cs-notification" id="notifTrigger" title="Notifications">
                            <i class="fa-regular fa-bell"></i>
                            <span class="cs-notification-badge" id="notifBadge">0</span>
                        </div>
                        <div class="cs-dropdown-panel" id="notifPanel">
                            <div class="cs-panel-header">
                                <span>Notifications</span>
                                <span style="font-size:0.75rem; color:#3b82f6; cursor:pointer;" id="clearAllNotifs">Clear all</span>
                            </div>
                            <div class="cs-panel-body" id="notifList"></div>
                        </div>
                    </div>

                    <div class="cs-profile-wrapper">

                        <div class="cs-profile" id="profileTrigger">
                            <div class="cs-profile-avatar">
                                <img src='<%=ph.getUserPhoto(session.getAttribute("uType").toString(), session.getAttribute("ss_id").toString())%>' alt='User' style="width: 30px;height: 30px;"></div>
                            <div class="cs-profile-details">
                                <%String userInfoJson = ph.getUserInfo(session.getAttribute("uType").toString(),session.getAttribute("ss_id").toString());%>
                                <span class="cs-profile-name">
                                    <span class="cs-profile-name-text" id="cs-profile-name"></span>
                                </span>
                                <span class="cs-profile-role">
                                    <span class="cs-profile-role-text" id="cs-profile-role"></span>
                                </span>
                                <script>
                                    var userInfo = <%=userInfoJson%>;
                                    document.getElementById("cs-profile-name").textContent = userInfo.user_name + " (" + userInfo.user_id + ")";
                                    document.getElementById("cs-profile-role").textContent = userInfo.designation;
                                </script>
                            </div>
                        </div>

                        <div class="cs-profile-dropdown" id="profileDropdown">
                            <!-- User Info tab at the top of the dropdown - name / id / designation -->
                            <div class="cs-profile-user-info">
                                <div class="cs-profile-user-info-row">
                                    <span class="cs-profile-user-info-label">Name</span>
                                    <span class="cs-profile-user-info-value" id="cs-profile-info-name"></span>
                                </div>
                                <div class="cs-profile-user-info-row">
                                    <span class="cs-profile-user-info-label">ID</span>
                                    <span class="cs-profile-user-info-value" id="cs-profile-info-id"></span>
                                </div>
                                <div class="cs-profile-user-info-row">
                                    <span class="cs-profile-user-info-label">Role</span>
                                    <span class="cs-profile-user-info-value" id="cs-profile-info-role"></span>
                                </div>
                            </div>
                            <script>
                                document.getElementById("cs-profile-info-name").textContent = userInfo.user_name || "";
                                document.getElementById("cs-profile-info-id").textContent = userInfo.user_id || "";
                                document.getElementById("cs-profile-info-role").textContent = userInfo.designation || "";
                            </script>
                            <div class="cs-profile-menu-divider"></div>
                            <div class="cs-profile-menu-item">
                                <i class="fa-solid fa-key"></i>
                                <a style="width:100%;" href="<%=request.getContextPath() + "/"%>JSP/Welcome/change_password.jsp"><span>Change Password</span></a>
                            </div>
                            <div class="cs-profile-menu-item danger">
                                <i class="fa-solid fa-right-from-bracket"></i>
                                <a style="width:100%;" href="<%=request.getContextPath() + "/"%>logout"><span>Sign Out</span></a>
                            </div>
                        </div>
                    </div>
                </div>
            </header>

            <!-- ==========================================
            MAIN CONTENT MIDDLE AREA
            ========================================== -->
            <main class="cs-container-full" id="mainContent">
                <%String resourceInfoJson = PageHeader.load_Resource_Info_Bar_JSON( host_string, request.getServletPath().substring(1), request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(), session.getAttribute("ss_id").toString());%>
                <div id="csResourceInfoBar"></div>

                <script>
                    var resourceInfo = <%=resourceInfoJson%>;
                    if (resourceInfo.status === "success") {
                        var html = "";
                        html += "<section class='cs-resource-info-bar'>";
                        html += "<ol style='border-radius:5px;' class='breadcrumb breadcrumb-bg-blue'>";
                        $.each(resourceInfo.breadcrumb, function (i, obj) {
                            html += "<li style='font-size:1.2rem;'>";
                            html += "<i class='material-icons'>" + (obj.icon || "link") + "</i> ";
                            if (obj.github_url && obj.label) {
                                html += "<a target='_blank' title='View source in GitHub' href='" + obj.github_url + "'>" + obj.label + "</a>";
                            } else {
                                html += "<span>" + (obj.label || "") + "</span>";
                            }
                            html += "</li>";
                        });

                        html += "<li style='font-size:1.2rem; float:right;'>";
                        html += "<a title='Goto Home Page' " + "href='" + resourceInfo.home_url + "'>";
                        html += "<i class='material-icons'>home</i>";
                        html += "</a>";
                        if (resourceInfo.resource_id !== "" && resourceInfo.resource_id !== "359") {
                            if (resourceInfo.pin_status === "pinned") {
                                html += "<a title='unpin this resource' href='javascript:void(0)' onclick='unpin_this_resource(" + resourceInfo.resource_id + ")'>";
                                html += "<i class='material-icons'>delete</i>";
                                html += "</a>";
                            } else {
                                html += "<a title='pin this resource' href='javascript:void(0)' onclick='pin_this_resource(" + resourceInfo.resource_id + ")'>";
                                html += "<i class='material-icons'>push_pin</i>";
                                html += "</a>";
                            }
                        }
                        
                        if (resourceInfo.resource_id !== "") {
                            html += "<a title='Info about this resource - Reserved for future use' data-toggle='modal' data-target='#largeModalinfo' onclick='showResourceDetails(" + resourceInfo.resource_id + ")' href='javascript:void(0)'>";
                            html += "<i class='material-icons'>info</i>";
                            html += "</a>";
                        }
                        
                        html += "</li></ol></section>";
                        $("#csResourceInfoBar").html(html);

                        function csHighlightActiveMenu() {
                            var breadcrumbIds = resourceInfo.breadcrumb.map(function (o) { return String(o.id); });
                            var currentUrl = window.location.pathname;
                            var $activeLi = null;
                            for (var i = breadcrumbIds.length - 1; i >= 0 && !$activeLi; i--) {
                                var $candidate = $("#menu_" + breadcrumbIds[i]);
                                if ($candidate.length) $activeLi = $candidate;
                            }
                            if (!$activeLi || !$activeLi.length) {
                                $("#sidebarMenu a").each(function () {
                                    var href = $(this).attr("href") || "";
                                    if (href && href !== "#" && href !== "javascript:void(0);"
                                        && currentUrl.indexOf(href.replace(/^https?:\/\/[^\/]+/, "")) !== -1) {
                                        $activeLi = $(this).closest("li");
                                        return false;
                                    }
                                });
                            }
                            if (!$activeLi || !$activeLi.length) return;

                            // $("#sidebarMenu li").removeClass("active");
                            // $activeLi.addClass("active");
                            // $activeLi.parents("li").each(function () {
                            //     $(this).addClass("open active-path");
                            //     $(this).children(".cs-submenu").show();
                            // });

                            $("#sidebarMenu li").removeClass("active");
                            $activeLi.addClass("active");
                            
                            $activeLi.children(".cs-submenu").show();
                            if ($activeLi.children(".cs-submenu").length) {
                                $activeLi.addClass("open");
                            }
                            
                            $activeLi.parents("li").each(function () {
                                $(this).addClass("open active-path");
                                $(this).children(".cs-submenu").show();
                            });
                                
                            var activeEl = $activeLi.get(0);
                            if (activeEl && activeEl.scrollIntoView) {
                                activeEl.scrollIntoView({ block: "center" });
                            }
                        }

                        if (document.querySelectorAll("#sidebarMenu li").length > 0) {
                            csHighlightActiveMenu();
                        } else {
                            document.addEventListener("sidebarMenuReady", csHighlightActiveMenu);
                        }
                    } else {
                        console.error("Resource Info Bar Error:", resourceInfo.message);
                    }
                </script>

    <%
        } catch (Exception e) {
        out.print(e.toString()); 
        }
	%>

    <div class="modal fade" id="largeModalinfo" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="largeModalLabelinfo">Resource Details</h4>
                </div>
                <div class="modal-body" id="resource_det" style="color: white"></div>
                <div class="modal-footer" style="background-color: #2460a7">
                    <button type="button" class="btn btn-link waves-effect"
                        data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="largeModalSSS" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="largeModalLabelSSS">Search Results</h4>
                </div>
                <div class="modal-body" id="loadSearchDetails"></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-link waves-effect"
                        data-dismiss="modal">CLOSE</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const hamburgerBtn = document.getElementById('hamburgerBtn');
            const mainContainer = document.getElementById('mainContainer');
            const sidebar = document.getElementById('sidebar');
            const backdrop = document.getElementById('sidebarBackdrop');
            const mobileSearchOpen = document.getElementById('mobileSearchOpen');
            const mobileSearchClose = document.getElementById('mobileSearchClose');
            const searchContainer = document.getElementById('searchContainer');
            const menuSearchInput = document.getElementById('menuSearchInput');
            const searchPalette = document.getElementById('searchPalette');
            const sidebarMenu = document.getElementById('sidebarMenu');
            const noResultsMsg = document.getElementById('noResultsMsg');
            const pageTitle = document.getElementById('pageTitle');
            const breadcrumbActive = document.getElementById('breadcrumbActive');
            const breadcrumb = document.getElementById('breadcrumb');
            
            function openSubmenu(liElement) {
                liElement.classList.add('open');
                const submenu = liElement.querySelector(':scope > .cs-submenu');
                if (submenu) {
                    submenu.style.display = 'block';
                }
            }
            
            function closeSubmenuTree(liElement) {
                liElement.classList.remove('open');
                const submenu = liElement.querySelector(':scope > .cs-submenu');
                if (submenu) {
                    submenu.style.display = 'none';
                    const innerOpenItems = submenu.querySelectorAll('li.open');
                    innerOpenItems.forEach(inner => {
                        inner.classList.remove('open');
                        const innerSub = inner.querySelector('.cs-submenu');
                        if (innerSub) innerSub.style.display = 'none';
                    });
                }
            }
            
            function setActiveMenuItem(targetLi) {
                const allItems = sidebarMenu.querySelectorAll('li');
                allItems.forEach(item => {
                    item.classList.remove('active', 'active-path');
                });
                
                targetLi.classList.add('active');
                
                const pathNames = [];
                let current = targetLi;
                
                while (current && current !== sidebarMenu) {
                    if (current.tagName === 'LI') {
                        const labelEl = current.querySelector(':scope > a .cs-menu-text');
                        const labelText = labelEl ? labelEl.textContent.trim() : '';
                        pathNames.unshift(labelText);
                        
                        if (current !== targetLi) {
                            current.classList.add('active-path');
                            openSubmenu(current);
                        }
                    }
                    current = current.parentElement;
                }
                
                const leafName = pathNames[pathNames.length - 1];
                if (pageTitle) pageTitle.textContent = leafName + " Overview";
                if (breadcrumbActive) breadcrumbActive.textContent = leafName;
                
                if (breadcrumb) {
                    let breadcrumbHTML = '<span>App</span>';
                    pathNames.forEach((name, index) => {
                        const isLast = index === pathNames.length - 1;
                        breadcrumbHTML += ' <i class="fa-solid fa-chevron-right" style="font-size:0.6rem;"></i> ';
                        if (isLast) {
                            breadcrumbHTML += '<span class="active-crumb">' + name + '</span>';
                        } else {
                            breadcrumbHTML += '<span>' + name + '</span>';
                        }
                    });
                    breadcrumb.innerHTML = breadcrumbHTML;
                }
            }
            
            function buildSearchIndex() {
                const items = [];
                const allLis = sidebarMenu.querySelectorAll('li');
                
                allLis.forEach(li => {
                    const link = li.querySelector(':scope > a');
                    const labelSpan = link.querySelector('.cs-menu-text');
                    if (!labelSpan) return;
                    
                    const title = labelSpan.textContent.trim();
                    const iconWrapper = link.querySelector('.cs-icon-wrapper');
                    const iconHTML = iconWrapper ? iconWrapper.innerHTML : '<i class="fa-solid fa-angle-right"></i>';
                    const href = link.getAttribute('href');
                    const hasChildren = !!li.querySelector(':scope > .cs-submenu');
                    
                    const pathSegments = [];
                    let parent = li;
                    while (parent && parent !== sidebarMenu) {
                        if (parent.tagName === 'LI') {
                            const pText = parent.querySelector(':scope > a .cs-menu-text')?.textContent.trim();
                            if (pText) pathSegments.unshift(pText);
                        }
                        parent = parent.parentElement;
                    }
                    
                    items.push({
                        element: li,
                        title: title,
                        iconHTML: iconHTML,
                        href: href,
                        hasChildren: hasChildren,
                        path: pathSegments.join(' > ')
                    });
                });
                return items;
            }
            
            function initMenuSearch() {
                const searchItems = buildSearchIndex();
                
                menuSearchInput.addEventListener('input', function () {
                    const query = this.value.toLowerCase().trim();
                    searchPalette.innerHTML = '';
                    
                    if (query === '') {
                        searchPalette.classList.remove('active');
                        return;
                    }
                    
                    const matches = searchItems.filter(item =>
                    item.title.toLowerCase().includes(query) ||
                    item.path.toLowerCase().includes(query)
                );
                
                if (matches.length === 0) {
                    searchPalette.innerHTML = '<div style="padding:10px; font-size:0.8rem; color:#64748b; text-align:center;">No matching routes found</div>';
                } else {
                    matches.forEach(match => {
                        const div = document.createElement('div');
                        div.className = 'cs-palette-item';
                        div.innerHTML =
                        '<span class="item-title">' + match.iconHTML + ' ' + match.title + '</span>' +
                        '<span class="item-path">' + match.path + '</span>';
                        div.addEventListener('click', () => {
                            searchPalette.classList.remove('active');
                            menuSearchInput.value = '';
                            
                            if (searchContainer.classList.contains('active')) {
                                searchContainer.classList.remove('active');
                                mobileSearchClose.classList.remove('active');
                            }
                            
                            if (window.innerWidth <= 768) {
                                closeMobileSidebar();
                            }
                            
                            if (mainContainer.classList.contains('sidebar-collapsed')) {
                                mainContainer.classList.remove('sidebar-collapsed');
                            }
                            
                            setActiveMenuItem(match.element);
                            
                            if (match.hasChildren) {
                                openSubmenu(match.element);
                                match.element.scrollIntoView({ block: 'center', behavior: 'smooth' });
                            } else if (match.href && match.href !== 'javascript:void(0);' && match.href !== '#') {
                                window.location.href = match.href;
                            }
                        });
                        searchPalette.appendChild(div);
                    });
                }
                searchPalette.classList.add('active');
            });
            
            document.addEventListener('click', (e) => {
                if (!searchContainer.contains(e.target)) {
                    searchPalette.classList.remove('active');
                }
            });
            
            const initialActive = sidebarMenu.querySelector('li.active');
            if (initialActive) {
                setActiveMenuItem(initialActive);
            }
        }
        
        document.addEventListener('sidebarMenuReady', initMenuSearch);
        
        const notifTrigger = document.getElementById('notifTrigger');
        const notifPanel = document.getElementById('notifPanel');
        const notifList = document.getElementById('notifList');
        const notifBadge = document.getElementById('notifBadge');
        const clearAllNotifs = document.getElementById('clearAllNotifs');
        
        function timeAgo(dateStr) {
            if (!dateStr) return '';
            const then = new Date(dateStr.replace(' ', 'T'));
            if (isNaN(then.getTime())) return '';
            const diffMs = Date.now() - then.getTime();
            const diffMins = Math.floor(diffMs / 60000);
            if (diffMins < 1) return 'Just now';
            if (diffMins < 60) return diffMins + 'm ago';
            const diffHours = Math.floor(diffMins / 60);
            if (diffHours < 24) return diffHours + 'h ago';
            return Math.floor(diffHours / 24) + 'd ago';
        }
        
        let notifications = [];
        if (typeof userNotifications !== 'undefined' && userNotifications && Array.isArray(userNotifications.notifications)) {
            notifications = userNotifications.notifications.map(function (n) {
                return {
                    id: n.sn_id,
                    title: n.message,
                    time: timeAgo(n.created_date)
                };
                
            });
        }
        
        function renderNotifications() {
            notifList.innerHTML = '';
            notifBadge.textContent = notifications.length;
            if (notifications.length === 0) {
                notifBadge.style.display = 'none';
                notifList.innerHTML = '<div style="padding:16px; text-align:center; font-size:0.8rem; color:#94a3b8;">No new notifications</div>';
                return;
            }
            notifBadge.style.display = 'flex';
            
            notifications.forEach(item => {
                const el = document.createElement('div');
                el.className = 'cs-notification-item';
                el.innerHTML =
                '<div>' +
                    '<div class="notif-text">' + item.title + '</div>' +
                    '<div class="notif-time">' + item.time + '</div>' +
                    '</div>' +
                    '<i class="fa-solid fa-xmark dismiss-btn"></i>';
                    
                    // Drag swipe support
                    let startX = 0;
                    el.addEventListener('touchstart', (e) => startX = e.touches[0].clientX);
                    el.addEventListener('touchmove', (e) => {
                        let diff = e.touches[0].clientX - startX;
                        if (diff > 0) el.style.transform = 'translateX(' + diff + 'px)';
                    });
                    el.addEventListener('touchend', (e) => {
                        let diff = e.changedTouches[0].clientX - startX;
                        if (diff > 60) {
                            removeNotif(item.id);
                        } else {
                            el.style.transform = 'translateX(0)';
                        }
                    });
                    
                    el.querySelector('.dismiss-btn').addEventListener('click', (e) => {
                        e.stopPropagation();
                        removeNotif(item.id);
                    });
                    
                    notifList.appendChild(el);
                });
            }
            
            function removeNotif(id) {
                notifications = notifications.filter(n => n.id !== id);
                renderNotifications();
            }
            
            clearAllNotifs.addEventListener('click', () => {
                notifications = [];
                renderNotifications();
            });
            
            const profileTrigger = document.getElementById('profileTrigger');
            const profileDropdown = document.getElementById('profileDropdown');
            
            notifTrigger.addEventListener('click', (e) => {
                e.stopPropagation();
                notifPanel.classList.toggle('active');
                profileDropdown.classList.remove('active');
            });
            
            renderNotifications();
            
            profileTrigger.addEventListener('click', (e) => {
                e.stopPropagation();
                profileDropdown.classList.toggle('active');
                notifPanel.classList.remove('active');
            });
            
            document.addEventListener('click', () => {
                notifPanel.classList.remove('active');
                profileDropdown.classList.remove('active');
            });
            
            hamburgerBtn.addEventListener('click', () => {
                if (window.innerWidth <= 768) {
                    toggleMobileSidebar();
                } else {
                    mainContainer.classList.toggle('sidebar-collapsed');
                }
            });
            
            function toggleMobileSidebar() {
                const isOpen = sidebar.classList.contains('mobile-open');
                if (isOpen) {
                    closeMobileSidebar();
                } else {
                    sidebar.classList.add('mobile-open');
                    backdrop.classList.add('active');
                }
            }
            
            function closeMobileSidebar() {
                sidebar.classList.remove('mobile-open');
                backdrop.classList.remove('active');
            }
            
            backdrop.addEventListener('click', closeMobileSidebar);
            
            if (mobileSearchOpen && mobileSearchClose && searchContainer) {
                mobileSearchOpen.addEventListener('click', () => {
                    searchContainer.classList.add('active');
                    mobileSearchClose.classList.add('active');
                    menuSearchInput.focus();
                });
                
                mobileSearchClose.addEventListener('click', () => {
                    searchContainer.classList.remove('active');
                    mobileSearchClose.classList.remove('active');
                    searchPalette.classList.remove('active');
                });
            }
            
            window.addEventListener('resize', () => {
                if (window.innerWidth > 768) {
                    sidebar.classList.remove('mobile-open');
                    backdrop.classList.remove('active');
                }
            });
        });
        
        document.addEventListener('DOMContentLoaded', function () {
            var sidebarMenu = document.getElementById('sidebarMenu');
            
            function getNodeData(node) {
                var label = node.label || node.title || node.name || 'Untitled';
                var link = (node.link && node.link !== '#') ? node.link : (node.url && node.url !== '#') ? node.url : 'javascript:void(0);';
                var rawIcon = node.icon || node.icon_class || node.iconClass || '';
                var isFontAwesome = rawIcon.indexOf('fa-') !== -1;
                var materialIconName = rawIcon && !isFontAwesome ? rawIcon : 'folder';
                var faIconClass = isFontAwesome ? rawIcon : 'fa-solid fa-folder';
                return {
                    label: label,
                    link: link,
                    isFontAwesome: isFontAwesome,
                    materialIconName: materialIconName,
                    faIconClass: faIconClass
                };
            }
            
            function buildMenuTree(items) {
                var itemMap = {};
                var rootItems = [];
                
                items.forEach(function (item) {
                    var id = item.resource_id || item.id;
                    itemMap[id] = Object.assign({}, item, { children: [] });
                });
                
                items.forEach(function (item) {
                    var id = item.resource_id || item.id;
                    var parentId = String(item.parent_id || item.parentId || "0");
                    var isTopLevel = (parentId === "0" || parentId === "null");
                    
                    if (isTopLevel) {
                        rootItems.push(itemMap[id]);
                    } else if (itemMap[parentId]) {
                        itemMap[parentId].children.push(itemMap[id]);
                    }
                });
                
                return rootItems;
            }
            
            function renderMenuNodes(nodes, isSubmenu) {
                isSubmenu = isSubmenu || false;
                if (!nodes || nodes.length === 0) return '';
                
                var html = isSubmenu ? '<ul class="cs-submenu" style="display:none;">' : '';
                    
                    nodes.forEach(function (node) {
                        var nodeData = getNodeData(node);
                        var label = nodeData.label;
                        var link = nodeData.link;
                        var hasChildren = node.children && node.children.length > 0;
                        var nodeId = node.resource_id || node.id || '';
                        var orderNum = node.order_number || node.sortorder || node.sort_order || '';
                        var tooltipText = nodeId + (orderNum ? ' - ' + orderNum : '');
                        
                        var iconMarkup = nodeData.isFontAwesome
                        ? '<i class="' + nodeData.faIconClass + '"></i>'
                        : '<span class="material-symbols-outlined">' + nodeData.materialIconName + '</span>';
                        
                        html += '<li id="menu_' + nodeId + '" title="' + tooltipText + '">';
                        html += '  <a href="' + link + '">';
                        html += '    <span class="cs-menu-label">';
                        html += '      <span class="cs-icon-wrapper">' + iconMarkup + '</span>';
                        html += '      <span class="cs-menu-text">' + label + '</span>';
                        html += '    </span>';
                        if (hasChildren) {
                            html += '    <i class="fa-solid fa-chevron-right cs-arrow"></i>';
                        }
                        html += '  </a>';
                        
                        if (hasChildren) {
                            html += renderMenuNodes(node.children, true);
                        }
                        
                        html += '</li>';
                    });
                    
                    if (isSubmenu) html += '</ul>';
                    return html;
                }
                
                if (typeof menuJson !== 'undefined' && Array.isArray(menuJson) && menuJson.length > 0) {
                    var menuTree = buildMenuTree(menuJson);
                    sidebarMenu.innerHTML = renderMenuNodes(menuTree);
                    initializeAccordion();
                } else {
                    console.warn('menuJson is empty or undefined.');
                }
                
                document.dispatchEvent(new CustomEvent('sidebarMenuReady'));
            });
            
            function initializeAccordion() {
                var sidebarMenu = document.getElementById('sidebarMenu');
                if (!sidebarMenu) return;
                
                var allLinks = sidebarMenu.querySelectorAll('a');
                
                allLinks.forEach(function (link) {
                    link.addEventListener('click', function (e) {
                        var parentLi = this.parentElement;
                        var childSubmenu = parentLi.querySelector(':scope > .cs-submenu');
                        var href = this.getAttribute('href') || '';
                        var isPureContainer = (href === '' || href === '#' || href === 'javascript:void(0);');
                        
                        if (childSubmenu) {
                            var isOpen = parentLi.classList.contains('open');
                            
                            var siblingLis = parentLi.parentElement.querySelectorAll(':scope > li');
                            siblingLis.forEach(function (sibling) {
                                if (sibling !== parentLi) {
                                    sibling.classList.remove('open');
                                    var siblingSub = sibling.querySelector(':scope > .cs-submenu');
                                    if (siblingSub) siblingSub.style.display = 'none';
                                }
                            });
                            
                            if (isOpen && isPureContainer) {
                                parentLi.classList.remove('open');
                                childSubmenu.style.display = 'none';
                            } else {
                                parentLi.classList.add('open');
                                childSubmenu.style.display = 'block';
                            }
                            
                            if (isPureContainer) {
                                e.preventDefault();
                            }
                            // else: let the browser navigate to the parent's own link
                        }
                    });
                });
            }
    </script>
            