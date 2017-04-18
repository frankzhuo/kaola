insert overwrite table merzx.risk_control_consulation
select tbr.case_id,tbr.mon_id,tbr.case_dt,tbr.rrc_typ,tbr.rsk_lvl,tbr.proc_rst,tbr.is_rsk,rcg.rrc_typ,lvl.rsk_lvl_dsc,tbr.tm_smp
from ods.xfx_posprrc_rrctcasebr tbr
left join ods.xfx_posprrc_rrctcasegath rcg 
   on tbr.case_no_fa=rcg.case_no_fa
left join ods.xfx_posprrc_rrctrsklvl lvl
   on tbr.rsk_lvl=lvl.rsk_lvl;
