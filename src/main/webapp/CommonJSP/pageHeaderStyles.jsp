<style>
.cs-content {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
}
.cs-card-header {
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
}
.cs-card-body {
    border-bottom-left-radius: 7px;
    border-bottom-right-radius: 7px;
}
.cs-card ,.cs-panel {
    width: 100%;
    box-shadow: var(--cs-shadow-a);
    display: flex;
    flex-direction: column;
    border-radius: 7px;
    border: 1px solid #334155 !important;    
}
.cs-card-header,
.cs-panel-header {
    background: var(--cs-text-menu-item,--cs-bgcolor-a);
    color: var(--cs-color-a);
    display: flex;
    align-items: center;
}
.cs-card-header {
    padding: 20px 10px; 
}
.cs-panel-header{
    padding: 7px 10px;
}
.cs-card-header h4{
    margin: 0 !important;
    font-size: 18px;
}
.cs-panel-header h4 {
    margin: 0 !important;
    font-size: 16px;
}
.cs-panel-header {
    cursor: pointer;
}
.cs-panel-icon {
    padding: 4px 8px;
}
.cs-card-body {
    padding: 1rem;
    background: var(--cs-bg-content-panel,--cs-bgcolor-b);
    display: flex;
    justify-content: center;
    flex-direction: column;
    gap: 1rem;
}
.cs-panel-body {
    min-height: 70px;
    padding: 1rem;
    background: var(--cs-bg-content-panel,--cs-bgcolor-b);
}
.cs-panel {
    border: 1px solid var(--cs-border-a);
    overflow: hidden;
}
.cs-panel.collapsed .cs-panel-body {
    display: none;
}
.cs-btn {
    border: none;
    padding: 8px 15px;
    border-radius: 5px;
    cursor: pointer;
    transition: .2s;
}
.cs-btn-orange {
    background: var(--cs-btn-a);
    color: #fff;
}
.cs-btn-orange:hover {
    background: var(--cs-btn-a-hover);
}
.cs-temp-a {
    margin-top: 1.5rem;
    display: flex;
    gap: 0.8rem;
    flex-wrap: wrap;
    justify-content: center;
}
.cs-temp-b {
    width: 100%;
    margin: 0 auto;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 1.5rem;
}
.cs-temp-b.cs-count-1 > * {
    grid-column: 2;
}
.cs-temp-b.cs-count-2 {
    grid-template-columns: repeat(2, 1fr);
    max-width: calc(66.666% - 0.5rem);
    margin: 0 auto;
}
.cs-temp-b.cs-count-4 > *:last-child {
    grid-column: 2;
}
.cs-temp-b.cs-count-5 > *:nth-child(4) {
    grid-column-start: 1;
    margin-left: calc(50% + 0.75rem);
}
.cs-temp-b.cs-count-5 > *:nth-child(5) {
    grid-column: 3;
    margin-left: 0;
}
.cs-center-option {
    display: flex;
    flex-direction: column;
    min-width: 0; 
}
.cs-center-option label {
    font-size: 14px;
    margin-bottom: 6px;
}
.cs-center-option select {
    padding: 6px 10px;
    border-radius: 6px;
}
.cs-center-option select:focus {
    outline: none;
    border-color: var(--cs-btn-a);
}
.cs-bootstrap {
    width: 100%;
    display: block;
}
.cs-bootstrap .bootstrap-select {
    width: 100% !important;
    display: block !important;
}
.cs-bootstrap .bootstrap-select > .dropdown-toggle {
    width: 100% !important;
    height: 40px !important;
    border: 1px solid #3466a7 !important;
    border-radius: 6px !important;
    background: #fff !important;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.cs-bootstrap .dropdown-menu {
    font-size: 14px;
    width: 100% !important;
}
.cs-table-a {
    width: 100%;
    overflow-x: auto;
}
.cs-table-a .cs-table-grid {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid #9aa7b6;
}
.cs-table-a .cs-table-grid th {
    background: var(--cs-bg-content-table-th);
    color: #fff;
    padding: 8px;
    text-align: left;
    border: 1px solid var(--cs-border-content-table-th);
    text-align: center;
}
.cs-table-a .cs-table-grid td {
    border: 1px solid var(--cs-border-content-table-td);
    text-align: center
}
.cs-table-a .cs-table-grid tbody tr:nth-child(odd) {
    background: #d6e5f8;
}
.cs-table-a .cs-table-grid tbody tr:nth-child(even) {
    background: #eef3fa;
}
.cs-table-a .cs-table-grid .cs-input {
    width: 100%;
    height: 32px;
    border: 1px solid var(--cs-text-side-bar-sub);
    background: #fff;
    padding: 4px 6px;
}
.cs-table-a .cs-table-grid td > span,
.cs-table-a .cs-table-grid td > input,
.cs-table-a .cs-table-grid td > select,
.cs-table-a .cs-table-grid td > div {
    padding: 6px;
	display: block;
    width: 100%;
}
/* ── Responsive ── */
@media (max-width: 992px) {
    .cs-temp-b {
        grid-template-columns: repeat(2, 1fr);
    }
}
@media (max-width: 768px) {
    .cs-temp-a {
        justify-content: center;
        margin-top: 1rem;        
    }
    .cs-table-a .cs-table-grid th,
    .cs-table-a .cs-table-grid td {
        padding: 6px;
        font-size: 13px;
        text-align: left;
    }
    .cs-table-a .cs-table-grid thead {
        display: none;
    }
    .cs-table-a .cs-table-grid,
    .cs-table-a .cs-table-grid tbody,
    .cs-table-a .cs-table-grid tr,
    .cs-table-a .cs-table-grid td {
        display: block;
        width: 100%;
    }
    .cs-table-a .cs-table-grid tr {
        border: 1px solid var(--cs-border-content-table-th);
        margin-bottom: 8px;
    }
    .cs-table-a .cs-table-grid td {
        display: grid;
        grid-template-columns: 120px 1fr;
        padding: 0;
        align-items: center;
        text-align: left;
    }
    .cs-table-a .cs-table-grid td::before {
        content: attr(data-cs-label);
        background: #cfd6df;
        padding: 6px 8px;
        font-weight: 500;
        border: 1px solid var(--cs-border-content-table-td);
        align-self: stretch;    
        display: flex;
        align-items: center;
    }
    .cs-table-a .cs-table-grid td > *{
        width:100%;
        display:block;
        padding:6px;
    }
    .cs-temp-b.cs-count-1{
        grid-template-columns:1fr;
    }
    
    .cs-temp-b.cs-count-1 > *{
        grid-column:auto;
    }
    .cs-table-a .cs-table-grid td span{
        padding-left: 4px;
        width: 100%;
        height: 100%;
    }
}
@media (max-width: 576px) {
    .cs-card-header h4,
    .cs-panel-header h4 {
        font-size: 13px;
    }
    .cs-temp-b {
        grid-template-columns: 1fr;
    }
    .cs-temp-b.cs-count-2 {
        grid-template-columns: 1fr;
        max-width: 100%;
    }
}
@media (max-width: 480px) {
    .cs-temp-a {
        flex-direction: column;
        width: 100%;
    }
    .cs-temp-a .cs-btn {
        width: 100%;
    }
}

.cs-table-b {
    width: 100%;
    overflow-x: auto;
}
.cs-table-b .cs-table-grid {
    width: 100%;
    border-collapse: collapse;
    border: 1px solid var(--cs-border-content-table-th);
}
.cs-table-b .cs-table-grid th {
    background: #4a6fa5;
    color: #fff;
    padding: 8px;
    text-align: left;
    border: 1px solid var(--cs-border-content-table-th);
    text-align: center;
}
.cs-table-b .cs-table-grid td {
    border: 1px solid var(--cs-border-content-table-td);
    text-align: center
}
.cs-table-b .cs-table-grid tbody tr:nth-child(odd) {
    background: #d6e5f8;
}
.cs-table-b .cs-table-grid tbody tr:nth-child(even) {
    background: #eef3fa;
}
.cs-table-b .cs-table-grid .cs-input {
    width: 100%;
    height: 32px;
    border: 1px solid var(--cs-border-content-table-td);
    background: #fff;
    padding: 4px 6px;
}
.cs-table-b .cs-table-grid td > span,
.cs-table-b .cs-table-grid td > input,
.cs-table-b .cs-table-grid td > select,
.cs-table-b .cs-table-grid td > div {
    padding: 6px;
	display: block;	
    width: 100%;
}
.cs-table-b .cs-table-grid td {
	display: grid;
	grid-template-columns: 120px 1fr;
    padding: 0;
    align-items: center;
    text-align: left;
}
.cs-table-b .cs-table-grid td::before {
    content: attr(data-cs-label);
    background: #cfd6df;
    padding: 6px 8px;
    font-weight: 500;
    border-right: 1px solid #9aa7b6;
    align-self: stretch;    
    display: flex;
    align-items: center;
}
.cs-table-b .cs-table-grid td > *{
    width:100%;
    display:block;
    padding:6px;
}
.cs-pagination {
    display: flex;
    justify-content: center;
    gap: 6px;
    margin-top: 15px;
    flex-wrap: wrap;
}
.cs-pagination .pg-btn {
    padding: 6px 12px;
    border: 1px solid #4a6fa5;
    background: white;
    color: #4a6fa5;
    cursor: pointer;
    border-radius: 6px;
    transition: 0.2s;
    font-size: 14px;
}
.cs-pagination .pg-btn:hover {
    background: #4a6fa5;
    color: white;
}
.cs-pagination .pg-btn.active {
    background: #4a6fa5;
    color: white;
    font-weight: bold;
}

.cs-table-grid{
	overflow: hidden;
}
.cs-checkbox {
    position: relative;
    display: inline-block;
    cursor: pointer;
    user-select: none;
}
.cs-checkbox input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
}
.cs-checkmark {
    width: 18px;
    height: 18px;
    background-color: #eee;
    border: 1px solid #4a6fa5;
    border-radius: 4px;
    display: inline-block;
    transition: all 0.2s ease;
}
.cs-checkbox:hover .cs-checkmark {
    background-color: #ddd;
}
.cs-checkbox input:checked + .cs-checkmark {
    background-color: #4a6fa5;
    border-color: #4a6fa5;
}
.cs-checkmark::after {
    content: "";
    position: absolute;
    display: none;
}
.cs-checkbox input:checked + .cs-checkmark::after {
    display: block;
}
.cs-checkbox .cs-checkmark::after {
    left: 5px;
    top: 1px;
    width: 5px;
    height: 10px;
    border: solid white;
    border-width: 0 2px 2px 0;
    transform: rotate(45deg);
}

.cs-temp-d{
    margin-top: 1.5rem;
    display: grid;
    grid-template-columns: repeat(4, max-content); 
    justify-content: center;                   
    gap: 1rem 2.5rem;
}
.cs-temp-d .cs-btn{
    min-width: 180px;   
    margin-top: 1rem;
}
@media (max-width: 768px) {
    .cs-temp-d {
        gap: 0.7rem;
        grid-template-columns: 1fr;
    }
}


.tbl_new,.tbl_new th,.tbl_new td,.tbl_new tr{border-color:#000!important}   
@media print {
    .tbl_new,
    .tbl_new tr,
    .tbl_new th,
    .tbl_new td {
        border: 1px solid #000 !important;
        border-color: #000 !important;
        -webkit-print-color-adjust: exact;
        print-color-adjust: exact;
    }
}

@media print {
    table.tbl_new {
        border-collapse: collapse !important;
    }
    table.tbl_new th,
    table.tbl_new td {
        border: 1px solid #000 !important;
    }
}

.pdf-overlay {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.65);
	z-index: 99999;
}
.pdf-overlay-content {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	color: #fff;
}
.pdf-overlay .glyphicon {
	font-size: 40px;
}
.spinning {
	animation: spin 1s linear infinite;
}

@keyframes spin {
    from { transform:rotate(0deg);	}
    to { transform: rotate(360deg); }
}
.scroll-row {
	overflow: auto;
	scrollbar-width: auto;
	scrollbar-color: #888 transparent;
}
.scroll-row::-webkit-scrollbar {
	width: 15px;
	height: 12px; 
}
.scroll-row::-webkit-scrollbar-track {
	background: transparent;
}
.scroll-row::-webkit-scrollbar-thumb {
	background-color: #888;
	border-radius: 6px;
}
.scroll-row::-webkit-scrollbar-thumb:hover {
	background-color: #555;
}
</style>

