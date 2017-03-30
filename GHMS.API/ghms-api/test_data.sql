-- tb_cam_dstr_cd
INSERT INTO public.tb_cam_dstr_cd (dstr_cd, dstr_cd_nm, dstr_cd_desc, del_yn, cret_dt, cretr_id, amd_dt, amdr_id) VALUES ('001', '전국', '전국', 'N', now(), 'admin', now(), 'admin');

-- tb_cam_svc_theme
INSERT INTO public.tb_cam_svc_theme (dstr_cd, svc_theme_cd, svc_theme_nm, svc_theme_desc, del_yn, cret_dt, cretr_id, amd_dt, amdr_id) VALUES ('001', 'HIT', '홈 IoT', '홈 IoT', 'N', now(), 'admin', now(), '');

-- tb_cam_unit_svc
INSERT INTO public.tb_cam_unit_svc (dstr_cd, svc_theme_cd, unit_svc_cd, unit_svc_nm, unit_svc_desc, insd_otsid_sys_gb, img_file_nm, trgt_subj_yn, admr_id, svc_ver, oprt_sttus_cd, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('001', 'HIT', '003', '홈 매니저', '올레 홈 매니저', '', '', '', '', 0, '', 'N', '', now(), '', now());

-- mbr_bas
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000001', 'ghms01', '8000000001', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000002', 'ghms02', '8000000002', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000003', 'ghms03', '8000000003', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000004', 'ghms04', '8000000004', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000005', 'kt01',   '8000000005', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000006', 'kt02',   '8000000006', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000007', 'ghms05', '8000000007', '2014-12-13 18:49');
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000008', 'ghms06', '8000000008', '2014-12-13 18:49');

INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000009', 'kt03',   '8000000009', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000010', 'kt04',   '8000000010', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000011', 'kt05',   '8000000011', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000012', 'kt06',   '8000000012', now());

-- svc_mbr_rel
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000001', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000002', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000003', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000004', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000005', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000006', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000007', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000008', '001', 'HIT', '003', '01');

INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000009', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000010', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000011', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000012', '001', 'HIT', '003', '01');

-- svc_tgt_bas
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000001', '7000000001', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000002', '7000000002', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000003', '7000000003', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000004', '7000000004', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000005', '7000000005', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000006', '7000000006', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000007', '7000000007', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('5000000008', '7000000008', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');

INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500101', '7000000009', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500102', '7000000010', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500103', '7000000011', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500104', '7000000012', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', '2015-02-09 20:49', '', now(), '');


-- svc_ets_rel
INSERT INTO public.svc_ets_rel(ets_mbr_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, cret_dt, amd_dt, svc_tgt_seq) VALUES ('7000000002', '7000000001', '001', 'HIT', '003', now(), null, '5000000001');
INSERT INTO public.svc_ets_rel(ets_mbr_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, cret_dt, amd_dt, svc_tgt_seq) VALUES ('7000000008', '7000000007', '001', 'HIT', '003', now(), null, '5000000007');


-- dev_model_bas
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000000', '000000000000', 'unknown_Device', '0000', 'unknown_Device', '', 'N', 'admin(언논)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000001', 'K9184381', 'SHP-GS100M', '0207', '삼성SDS', '', 'N', 'admin(허브)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000002', '022e00010001', 'SHP-DP710MK', '4003', '삼성SDS', '', 'N', 'admin(도어락)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000003', '022e00040004', 'SHP-SB100Z', '0701', '삼성SDS', '', 'N', 'admin(침임감지)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000004', '022a01000100', 'SHP-ZHY1505A', '1006', '타임밸브', '', 'N', 'admin(가스밸브)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000005', '022D01000001', 'Mercury', '0207', 'Mercury', '', 'N', 'admin(허브)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000006', 'GiGAWiFihome', 'KT', 'G000', 'KT', '', 'N', 'admin(AP)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000007', 'GiGAWiFiKTHOMESVC', 'KT', 'G001', 'KT', '', 'N', 'admin(AP)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000008', 'GiGAWiFiPC', 'KT', 'G002', 'KT', '', 'N', 'admin(AP)', now(), 'admin', now());
INSERT INTO dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000009', 'GiGAWiFiInternet', 'KT', 'G003', 'KT', '', 'N', 'admin(AP)', now(), 'admin', now());
/*
INSERT INTO public.dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000005', 'KTH00001', 'ZWAVE001', '0044', '삼성SDS', '', 'N', 'admin', '2015-2-13', 'admin', now());
INSERT INTO public.dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000006', 'KTH00002', 'ZWAVE002', '0045', '삼성SDS', '', 'N', 'admin', '2015-2-13', 'admin', now());
INSERT INTO public.dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000007', 'KTH00003', 'ZWAVE003', '0046', '삼성SDS', '', 'N', 'admin', '2015-2-13', 'admin', now());
INSERT INTO public.dev_model_bas (dev_model_seq, dev_model_cd, dev_model_nm, dev_type_cd, terml_makr_nm, pform_cns_elect_val, del_yn, cretr_id, cret_dt, amdr_id, amd_dt) VALUES ('1000000008', 'KTH00004', 'ZWAVE004', '0047', '삼성SDS', '', 'N', 'admin', '2015-2-13', 'admin', now());
*/

-- spot_dev_bas
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000001', '5000000001', 'df78hjwe-r8f8-s92k-9rjk-2hsy4kdi0001', '', '1000000001', 'iothub', 'IoT허브1', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000002', '5000000001', 'df78hjwe-r8f8-s92k-9rjk-2hsy4kdi0011', '', '1000000002', 'iotdevice01', '도어락1', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000001');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000003', '5000000001', 'df78hjwe-r8f8-s92k-9rjk-2hsy4kdi0012', '', '1000000003', 'iotdevice03', '침입감지1', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000001');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000004', '5000000001', 'df78hjwe-r8f8-s92k-9rjk-2hsy4kdi0013', '', '1000000004', 'iotdevice02', '가스밸브1', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000001');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000005', '5000000002', '9739e0c2-17ec-41d3-aa76-579bd34979be', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000006', '5000000002', 'be35264f-6a23-4cb1-9450-995b34e4d9ce', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000005');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000007', '5000000002', '7602310d-8600-4d0e-89e5-2e51dcf8d2fe', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000005');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000008', '5000000002', '7602310d-8600-4d0e-89e5-2e51dcfaaaae', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000005');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000009', '5000000003', '9739e0c2-17ec-41d3-aa76-579bd34979aa', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000010', '5000000003', 'be35264f-6a23-4cb1-9450-995b34e4d9vb', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000009');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000011', '5000000003', '7602310d-8600-4d0e-89e5-2e51dcf8d2dc', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000009');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000012', '5000000003', '7602310d-8600-4d0e-89e5-2e51dcfaaawa', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000009');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000013', '5000000004', '9739e0c2-17ec-41d3-aa76-579bd34979ye', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000014', '5000000004', 'be35264f-6a23-4cb1-9450-995b34e4d9he', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000013');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000015', '5000000004', '7602310d-8600-4d0e-89e5-2e51dcf8d2ge', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000013');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000016', '5000000004', '7602310d-8600-4d0e-89e5-2e51dcfaaafe', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000013');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000017', '5000000005', '9739e0c2-17ec-41d3-aa76-579bd34978ye', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000018', '5000000005', 'be35264f-6a23-4cb1-9450-995b34e4d6he', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000017');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000019', '5000000005', '7602310d-8600-4d0e-89e5-2e51dcf8d4ge', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000017');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000020', '5000000005', '7602310d-8600-4d0e-89e5-2e51dcfaa4fe', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000017');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000021', '5000000006', '9739e0c2-17ec-41d3-2a76-579bd34978ye', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000022', '5000000006', 'be35264f-6a23-4cb1-3450-995b34e4d6he', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000021');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000023', '5000000006', '7602310d-8600-4d0e-49e5-2e51dcf8d4ge', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000021');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000024', '5000000006', '7602310d-8600-4d0e-59e5-2e51dcfaa4fe', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000021');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000025', '5000000007', '9739e0c2-17ec-41d3-2a76-579bd34978ye', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000026', '5000000007', 'be35264f-6a23-4cb1-3450-995b34e4d6he', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000025');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000027', '5000000007', '7602310d-8600-4d0e-49e5-2e51dcf8d4ge', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000025');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000028', '5000000007', '7602310d-8600-4d0e-59e5-2e51dcfaa4fe', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000025');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000029', '5000000008', '9739e0c2-17ec-41d3-2a76-579bd34978ye', '', '1000000001', '00:18:8C:38:01:B1', '메인GW', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, null);
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000030', '5000000008', 'be35264f-6a23-4cb1-3450-995b34e4d6he', '', '1000000002', '00:18:8C:38:01:B2', '현관도어락', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000029');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000031', '5000000008', '7602310d-8600-4d0e-49e5-2e51dcf8d4ge', '', '1000000003', '00:18:8C:38:01:B3', '안방침입감지센서', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000029');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_group_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, ipadr, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, last_mtn_dt, eqp_lo_sbst, trobl_admr_id, dev_sttus_cd, latit_val, latit_div_cd, lngit_div_cd, lngit_val, altt, rmark, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq) VALUES ('2000000032', '5000000008', '7602310d-8600-4d0e-59e5-2e51dcfaa4fe', '', '1000000004', '00:18:8C:38:01:B4', '부엌가스벨브', 'Y', 'Y', 'N', '', '1001', '', 0, 0, 0, 0, '', '01.00', '', null, '', '', '', 0, '0', '1', 0,0, '', 'N', '', '2014-12-19 22:13', '', null, '2000000029');
-- svc_tgt_conn_bas
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000001', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000002', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000003', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000004', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000005', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000006', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000007', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');
INSERT INTO public.svc_tgt_conn_bas (conn_terml_id, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_tgt_conn_nm, pns_reg_id, appl_ver, tel_no) VALUES ('00000000-0423-3e86-ae39-066829ef01e8', '7000000008', '001', 'HIT', '003', '', 'QgAAADxenH6AqUmaPBJ6OljukYvJ76LCdHzFr+/ELgf6L5XQ7hT+ipmPJlXPh+86Igvm2aml5hcAy987n0mhd7EwlBXSfQA=', '1.0.0', '01055145994');

/* 사용자 장비 권한 추가 */
INSERT INTO MBR_SPOT_DEV_SETUP_TXN
SELECT
	A.MBR_SEQ
	, A.DSTR_CD
	, A.SVC_THEME_CD
	, A.UNIT_SVC_CD
	, B.SVC_TGT_SEQ
	, B.SPOT_DEV_SEQ
	, 'A902' AS GROUP_SETUP_CD
	, '003' AS SETUP_CD
	, 'Y' AS SETUP_VAL
FROM (
	SELECT
		SVC_TGT_SEQ
		, MBR_SEQ
		, DSTR_CD
		, SVC_THEME_CD
		, UNIT_SVC_CD
	FROM SVC_TGT_BAS
	WHERE DSTR_CD = '001'
	AND SVC_THEME_CD = 'HIT'
	AND UNIT_SVC_CD = '003'
	AND OPRT_STTUS_CD = '0001'
	UNION ALL
	SELECT
		A.SVC_TGT_SEQ
		, A.ETS_MBR_SEQ AS MBR_SEQ
		, A.DSTR_CD
		, A.SVC_THEME_CD
		, A.UNIT_SVC_CD
	FROM SVC_ETS_REL A
	, SVC_MBR_REL B
	, SVC_TGT_BAS C
	WHERE A.MBR_SEQ = B.MBR_SEQ
	AND B.MBR_SEQ = C.MBR_SEQ
	AND A.DSTR_CD = B.DSTR_CD
	AND B.DSTR_CD = C.DSTR_CD
	AND A.SVC_THEME_CD = B.SVC_THEME_CD
	AND B.SVC_THEME_CD = C.SVC_THEME_CD
	AND A.UNIT_SVC_CD = B.UNIT_SVC_CD
	AND B.UNIT_SVC_CD = C.UNIT_SVC_CD
	AND A.SVC_TGT_SEQ = C.SVC_TGT_SEQ
	AND A.DSTR_CD = '001'
	AND A.SVC_THEME_CD = 'HIT'
	AND A.UNIT_SVC_CD = '003'
	AND C.OPRT_STTUS_CD = '0001'
) A
, SPOT_DEV_BAS B
WHERE A.SVC_TGT_SEQ = B.SVC_TGT_SEQ
AND B.UP_SPOT_DEV_SEQ IS NOT NULL
--AND A.MBR_SEQ = 7000000001
ORDER BY A.MBR_SEQ, B.SPOT_DEV_SEQ;

/* 사용자 장비 알림 추가 */
INSERT INTO MBR_SPOT_DEV_SETUP_TXN
SELECT
	A.MBR_SEQ
	, A.DSTR_CD
	, A.SVC_THEME_CD
	, A.UNIT_SVC_CD
	, B.SVC_TGT_SEQ
	, B.SPOT_DEV_SEQ
	, 'A902' AS GROUP_SETUP_CD
	, '001' AS SETUP_CD
	, '["BATTERY","SECURITY","REMOTE"]' AS SETUP_VAL
FROM (
	SELECT
		SVC_TGT_SEQ
		, MBR_SEQ
		, DSTR_CD
		, SVC_THEME_CD
		, UNIT_SVC_CD
	FROM SVC_TGT_BAS
	WHERE DSTR_CD = '001'
	AND SVC_THEME_CD = 'HIT'
	AND UNIT_SVC_CD = '003'
	AND OPRT_STTUS_CD = '0001'
	UNION ALL
	SELECT
		A.SVC_TGT_SEQ
		, A.ETS_MBR_SEQ AS MBR_SEQ
		, A.DSTR_CD
		, A.SVC_THEME_CD
		, A.UNIT_SVC_CD
	FROM SVC_ETS_REL A
	, SVC_MBR_REL B
	, SVC_TGT_BAS C
	WHERE A.MBR_SEQ = B.MBR_SEQ
	AND B.MBR_SEQ = C.MBR_SEQ
	AND A.DSTR_CD = B.DSTR_CD
	AND B.DSTR_CD = C.DSTR_CD
	AND A.SVC_THEME_CD = B.SVC_THEME_CD
	AND B.SVC_THEME_CD = C.SVC_THEME_CD
	AND A.UNIT_SVC_CD = B.UNIT_SVC_CD
	AND B.UNIT_SVC_CD = C.UNIT_SVC_CD
	AND A.SVC_TGT_SEQ = C.SVC_TGT_SEQ
	AND A.DSTR_CD = '001'
	AND A.SVC_THEME_CD = 'HIT'
	AND A.UNIT_SVC_CD = '003'
	AND C.OPRT_STTUS_CD = '0001'
) A
, SPOT_DEV_BAS B
WHERE A.SVC_TGT_SEQ = B.SVC_TGT_SEQ
AND B.UP_SPOT_DEV_SEQ IS NOT NULL
--AND A.MBR_SEQ = 7000000001
ORDER BY A.MBR_SEQ, B.SPOT_DEV_SEQ;

/* 샌싱태그 마스터 */
insert into SNSN_TAG_CD_BAS
(snsn_tag_cd, snsn_tag_nm, snsn_tag_type_cd, snsn_tag_desc, use_yn, del_yn, cret_dt)
values
('50997105', '통보', '50', '통보', 'Y', 'N', now());

insert into SNSN_TAG_CD_BAS
(snsn_tag_cd, snsn_tag_nm, snsn_tag_type_cd, snsn_tag_desc, use_yn, del_yn, cret_dt)
values
('50996203', '상태', '50', '상태', 'Y', 'N', now());

insert into SNSN_TAG_CD_BAS
(snsn_tag_cd, snsn_tag_nm, snsn_tag_type_cd, snsn_tag_desc, use_yn, del_yn, cret_dt)
values
('50998003', '배터리', '50', '배터리', 'Y', 'N', now());

/* 장치모델별센싱태그관계 */
INSERT INTO DEV_MODEL_BY_SNSN_TAG_REL
(
	DEV_MODEL_SEQ, SNSN_TAG_cD, DEL_YN, CRETR_ID, CRET_DT
)
SELECT
	1000000002
	, SNSN_TAG_CD
	, 'N'
	, NULL
	, NOW()
FROM SNSN_TAG_CD_BAS
WHERE SNSN_TAG_NM IN ('상태','통보','배터리');

INSERT INTO DEV_MODEL_BY_SNSN_TAG_REL
(
	DEV_MODEL_SEQ, SNSN_TAG_cD, DEL_YN, CRETR_ID, CRET_DT
)
SELECT
	1000000004
	, SNSN_TAG_CD
	, 'N'
	, NULL
	, NOW()
FROM SNSN_TAG_CD_BAS
WHERE SNSN_TAG_NM IN ('상태','통보','배터리');

INSERT INTO DEV_MODEL_BY_SNSN_TAG_REL
(
	DEV_MODEL_SEQ, SNSN_TAG_cD, DEL_YN, CRETR_ID, CRET_DT
)
SELECT
	1000000003
	, SNSN_TAG_CD
	, 'N'
	, NULL
	, NOW()
FROM SNSN_TAG_CD_BAS
WHERE SNSN_TAG_NM IN ('통보','배터리');

/* 현장장치설정기본 */
INSERT INTO SPOT_DEV_SETUP_BAS
(SVC_TGT_SEQ, SPOT_DEV_SEQ, SNSN_TAG_CD, DEL_YN)
SELECT
	A.SVC_TGT_SEQ, A.SPOT_DEV_SEQ, B.SNSN_TAG_CD, 'N'
FROM SPOT_DEV_BAS A
, DEV_MODEL_BY_SNSN_TAG_REL B
WHERE A.DEV_MODEL_SEQ = B.DEV_MODEL_SEQ
AND A.DEV_MODEL_SEQ = 1000000002;

INSERT INTO SPOT_DEV_SETUP_BAS
(SVC_TGT_SEQ, SPOT_DEV_SEQ, SNSN_TAG_CD, DEL_YN)
SELECT
	A.SVC_TGT_SEQ, A.SPOT_DEV_SEQ, B.SNSN_TAG_CD, 'N'
FROM SPOT_DEV_BAS A
, DEV_MODEL_BY_SNSN_TAG_REL B
WHERE A.DEV_MODEL_SEQ = B.DEV_MODEL_SEQ
AND A.DEV_MODEL_SEQ = 1000000003;

INSERT INTO SPOT_DEV_SETUP_BAS
(SVC_TGT_SEQ, SPOT_DEV_SEQ, SNSN_TAG_CD, DEL_YN)
SELECT
	A.SVC_TGT_SEQ, A.SPOT_DEV_SEQ, B.SNSN_TAG_CD, 'N'
FROM SPOT_DEV_BAS A
, DEV_MODEL_BY_SNSN_TAG_REL B
WHERE A.DEV_MODEL_SEQ = B.DEV_MODEL_SEQ
AND A.DEV_MODEL_SEQ = 1000000004;

/* 회원비밀번호내역 시퀀스 */
CREATE SEQUENCE seq_mbr_pwd_txn
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;
ALTER TABLE seq_mbr_pwd_txn
  OWNER TO sdb;
COMMENT ON SEQUENCE seq_mbr_pwd_txn
  IS '회원비밀번호내역 시퀀스';

/* 비상연락망 등록 시퀀스 */
CREATE SEQUENCE seq_egncy_cntc_net_txn
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;
ALTER TABLE seq_egncy_cntc_net_txn
  OWNER TO sdb;
COMMENT ON SEQUENCE seq_egncy_cntc_net_txn
  IS '비상연락망 등록 시퀀스';

/* 파일관리내역 시퀀스 */
CREATE SEQUENCE seq_file_adm_txn
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 20;
ALTER TABLE seq_file_adm_txn
  OWNER TO sdb;
COMMENT ON SEQUENCE seq_file_adm_txn
  IS '파일관리내역 시퀀스';

/* 현장장치 확장 기본(MacAddr) */ 
INSERT INTO SPOT_DEV_EXPNSN_BAS
SELECT SVC_TGT_SEQ, SPOT_DEV_SEQ, 'gwMac', '98:93:CC:31:91:3B' FROM SPOT_DEV_BAS
WHERE SPOT_DEV_SEQ IN (
SELECT DISTINCT UP_SPOT_DEV_SEQ FROM SPOT_DEV_BAS
WHERE UP_SPOT_DEV_SEQ IS NOT NULL);

SELECT * FROM SPOT_DEV_EXPNSN_BAS
WHERE SPOT_DEV_SEQ IN (
SELECT DISTINCT UP_SPOT_DEV_SEQ FROM SPOT_DEV_BAS
WHERE UP_SPOT_DEV_SEQ IS NOT NULL);



/* 2015.03.27 GW 추가 */
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000009', 'kt03',   '8000000009', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000010', 'kt04',   '8000000010', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000011', 'kt05',   '8000000011', now());
INSERT INTO public.mbr_bas (mbr_seq, mbr_id, credential_id, cret_dt) VALUES ('7000000012', 'kt06',   '8000000012', now());

INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000009', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000010', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000011', '001', 'HIT', '003', '01');
INSERT INTO public.svc_mbr_rel (mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_sttus_cd) VALUES ('7000000012', '001', 'HIT', '003', '01');

INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500101', '7000000009', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', now(), '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500102', '7000000010', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', now(), '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500103', '7000000011', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', now(), '', now(), '');
INSERT INTO public.svc_tgt_bas (svc_tgt_seq, mbr_seq, dstr_cd, svc_theme_cd, unit_svc_cd, svc_cont_id, svc_tgt_id, svc_tgt_type_cd, svc_tgt_nm, oprt_sttus_cd, dtl_info_yn, old_zipcd, old_adr1, old_adr2, new_bldg_mgt_no, new_zipcd, new_adr1, new_adr2, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, org_cd) VALUES ('500104', '7000000012', '001', 'HIT', '003', '', '', '', 'GHMS', '0001', '', '', '', '', '', '', '', '', 'N', '', now(), '', now(), '');

INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, dev_sttus_cd, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq, latit_div_cd, lngit_div_cd) 
VALUES 
(1, 500101, 'C_B479A7171CAD', '1000000001', 'C_B479A7171CAD', 'IoT허브1', 'Y', 'Y', 'Y', 'F02641FD-C9A7-4F34-96F7-85C0DF65E551', '4', 30, null, null, null, null, null, '0.1', '1', 'N', 'admin', now(), 'admin', now(), null, '', '');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, dev_sttus_cd, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq, latit_div_cd, lngit_div_cd) 
VALUES 
(1, 500102, 'C_B479A7171C85', '1000000001', 'C_B479A7171C85', 'IoT허브2', 'Y', 'Y', 'Y', 'F02641FD-C9A7-4F34-96F7-85C0DF65E552', '4', 30, null, null, null, null, null, '0.1', '1', 'N', 'admin', now(), 'admin', now(), null, '', '');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, dev_sttus_cd, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq, latit_div_cd, lngit_div_cd) 
VALUES 
(1, 500103, 'C_B479A7171905', '1000000001', 'C_B479A7171905', 'IoT허브3', 'Y', 'Y', 'Y', 'F02641FD-C9A7-4F34-96F7-85C0DF65E553', '4', 30, null, null, null, null, null, '0.1', '1', 'N', 'admin', now(), 'admin', now(), null, '', '');
INSERT INTO public.spot_dev_bas (spot_dev_seq, svc_tgt_seq, dev_uu_id, dev_model_seq, spot_dev_id, dev_nm, phys_dev_yn, use_yn, tmp_dev_yn, athn_no, athn_forml_cd, colec_cycl_time, idle_jdgm_base_time, recn_cycl_time, recn_try_tmscnt, prod_seq, reg_seq, frmwr_ver_no, dev_sttus_cd, del_yn, cretr_id, cret_dt, amdr_id, amd_dt, up_spot_dev_seq, latit_div_cd, lngit_div_cd) 
VALUES 
(1, 500104, 'C_B479A71716FB', '1000000001', 'C_B479A71716FB', 'IoT허브4', 'Y', 'Y', 'Y', 'F02641FD-C9A7-4F34-96F7-85C0DF65E554', '4', 30, null, null, null, null, null, '0.1', '1', 'N', 'admin', now(), 'admin', now(), null, '', '');