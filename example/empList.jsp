<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="dataList" value="${requestScope['dataList']}" />
<c:set var="emp" value="${requestScope['EMPLOYEE']}" />
<div id="breadcrumbs" class="rcrumbs">
    <ul>
        <li class="show"><a href="index.iconext">Home</a><span class="divider">&gt;</span></li>
        <li class="show"><a href="#">Master</a><span class="divider">&gt;</span></li>
        <li class="show"><a href="empList.iconext">Employee MA</a><span class="divider">&gt;</span></li>
    </ul>
</div>
<style>
    .ms2side__header {
        background-color: #EEEEFF;
        color: #000088;
        height: 24px;
        margin-left: 3px;
        width: 250px;
    }
    .ms2side__div select {
        float: left;
        width: 350px;
    }

    .ms2side__header input {
        height: 20px;
        width: 150px;
    }
    .modalDialog {
        position: fixed;
        font-family: Arial, Helvetica, sans-serif;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background: rgba(0,0,0,0.8);
        z-index: 99999;
        opacity:0;
        -webkit-transition: opacity 400ms ease-in;
        -moz-transition: opacity 400ms ease-in;
        transition: opacity 400ms ease-in;
        pointer-events: none;
    }

    .modalDialog:target {
        opacity:1;
        pointer-events: auto;
    }

    .modalDialog > div {
        width: 760px;
        height: 400px;
        position: relative;
        margin: 10% auto;
        padding: 5px 20px 13px 20px;
        border-radius: 10px;
        background: #fff;
        background: -moz-linear-gradient(#fff, #999);
        background: -webkit-linear-gradient(#fff, #999);
        background: -o-linear-gradient(#fff, #999);
    }

    .close {
        background: #606061;
        color: #FFFFFF;
        line-height: 25px;
        position: absolute;
        right: -12px;
        text-align: center;
        top: -10px;
        width: 24px;
        text-decoration: none;
        font-weight: bold;
        -webkit-border-radius: 12px;
        -moz-border-radius: 12px;
        border-radius: 12px;
        -moz-box-shadow: 1px 1px 3px #000;
        -webkit-box-shadow: 1px 1px 3px #000;
        box-shadow: 1px 1px 3px #000;
    }

    .close:hover { background: #00d9ff; }
</style>
<script type="text/javascript" charset="utf-8">
    $(document).ready(function() {
        var table = $('#example').dataTable({bJQueryUI: true,
            "sPaginationType": "full_numbers",
            "bAutoWidth": false
        });

        $('#example tbody').on('click', 'tr', function() {
            if ($(this).hasClass('row_selected')) {
                $(this).removeClass('row_selected');
                $('#hdGridSelected').val('');
            }
            else {
                table.$('tr.row_selected').removeClass('row_selected');
                $(this).addClass('row_selected');
                $('#hdGridSelected').val($('#example tbody tr.row_selected').attr("id"));
            }
        });

        $('.dataTables_length').after($('#addEmp'));
        $('#addEmp').after($('#editEmp'));
        $('#editEmp').after($('#deleteEmp'));
        $('.dataTables_length label').remove();

        $('#addEmp').click(function() {
            $('#actionEMForm').val("add");
            $("#dialog").dialog('open');
            
        });

        $("#dialog").dialog({
            autoOpen: false,
            height: 480,
            width: 300,
            modal: false,
            buttons: {
                "ok": function() {
                    
                    if(($('#title').val() == '')||($('#name').val() == '')||($('#surname').val() == '')||($('#nickname').val() == '')||($('#position').val() == '')||($('#group').val() == '')){
                        alert("please fill all input");
                    }else{
                        $('#DialogEmpList').submit();
                    }
                    
                },
                "cancel": function() {
                    $(this).dialog("close");
                }
            },close : function() {
               $('#dialog input').val('');        
            }
        });
        
        $('#editEmp').click(function() {
            var id = $('#hdGridSelected').val();
            var DialogSize = 6;
            if (id == '') {
                alert('Please, choose the row which to edit');
            } else {
                $('#action').val("edit");
                for(var i =0;i<=DialogSize;i++){
                    $("#dialog input:eq("+i+")").val($("#example tbody tr.row_selected td:eq("+i+")").text())
                }
                $('#actionEMForm').val('edit');
                $("#dialog").dialog('open');
              //  $('#formEmpList').submit();
            }

        });

        $('#deleteEmp').click(function() {
            var id = $('#hdGridSelected').val();
            if (id == '') {
                alert('Please, choose the row which to delete');
            } else {
                //    setData();
                var result =confirm('Are you Sure to Delete?');
                if(result){
                    $('#action').val("delete");
                    $('#formEmpList').submit();
                }
            }
        });

    });
</script>


<form action="empList.iconext" method="post" id="formEmpList">
    <fieldset>
        <table width="100%">
            <tr>
                <td>
                    <p>
                        <label for="lbGroup">Group : </label>
                        <input type="text" name="txtGroup" id="txtGroup" value="${emp['groupCode']}" />
                    </p>

                </td>
                <td>
                    <p>
                        <label for="lbPosition">Position : </label>
                        <input type="text" name="txtPosition" id="txtPosition" value="${emp['positionCode']}"/>
                    </p>

                </td>

            </tr>
            <tr>
                <td>
                    <p>
                        <label for="lbEmployeeId">Employee ID : </label>
                        <input type="text" name="txtEmployeeId" id="txtEmployeeId" value="${emp['empID']}" />
                    </p>
                </td>
                <td>
                    <p>
                        <label for="lbEmployeeName">Name : </label>
                        <input type="text" name="txtEmployeeName" id="txtEmployeeName" value="${emp['name']}"/>
                    </p>
                </td>
            </tr>
            <tr>
                <td>
                    <button type="button" onclick="resetAction();">reset</button>
                </td>
                <td>
                    <button type="button" onclick="searchAction();">search</button>
                </td>
            </tr>
        </table>
    </fieldset>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
        <thead>
            <tr>
                <th style="width: 10%;">empID</th>
                <th style="width: 10%;">titleName</th>
                <th style="width: 15%;">name</th>
                <th style="width: 15%;">surName</th>
                <th style="width: 10%;">nickName</th>
                <th style="width: 20%;">positionCode</th>
                <th style="width: 20%;">groupCode</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="table" items="${dataList}">
                <tr id="${table.empID}">
                    <td><c:out value="${table.empID}" /></td>
                    <td><c:out value="${table.titleName}" /></td>
                    <td><c:out value="${table.name}" /></td>
                    <td><c:out value="${table.surName}" /></td>
                    <td><c:out value="${table.nickName}" /></td>
                    <td><c:out value="${table.positionCode}" /></td>
                    <td><c:out value="${table.groupCode}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a id="addEmp" class="add_row ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary" role="button" href="#openModal">

        <span class="ui-button-text">Add</span>
    </a>
    <a id="editEmp" class="add_row ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary" role="button" href="#openModal">

        <span class="ui-button-text">Edit</span>
    </a>
    <a id="deleteEmp" class="add_row ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary" role="button" href="#openModal">

        <span class="ui-button-text">Del</span>
    </a>
    <input type="hidden" id="action" name="action"/>
    <input type="hidden" id="hdGroup" name="hdGroup"/>
    <input type="hidden" id="hdPosition" name="hdPosition"/>
    <input type="hidden" id="hdEmployeeId" name="hdEmployeeId"/>
    <input type="hidden" id="hdEmployeeName" name="hdEmployeeName"/>
    <input type="hidden" id="hdGridSelected" name="hdGridSelected"/>
</form>
<div id="dialog" title="Add Employee">
    <form action="empList.iconext" method="post" id="DialogEmpList" >
        <input type="hidden" id="EMID"  name="EMID" value="add"/>
        <label>TITLE</label>
        <input id="title" name="title" type="text">
        <label>NAME</label>
        <input id="name" name="name" type="text">
        <label>SURNAME</label>
        <input id="surname" name="surname" type="text">
        <label>NICKNAME</label>
        <input id="nickname" name="nickname" type="text">
        <label>POSITION</label>
        <input id="position" name="position" type="text">
        <label>GROUP</label>
        <input id="group" name="group" type="text">    
        <input type="hidden" id="actionEMForm"  name="action" value="add"/>  
    </form>
</div>
<script type="text/javascript" src="js/empList.js"></script> 
