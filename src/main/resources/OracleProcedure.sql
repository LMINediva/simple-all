create or replace procedure SELECT_COUNTRIES(
    ref_cur1 out sys_refcursor,
    ref_cur2 out sys_refcursor) is
begin
    open ref_cur1 for select * from country where id < 3;
    open ref_cur1 for select * from country where id >= 3;
end SELECT_COUNTRIES;