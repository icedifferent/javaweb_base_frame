package com.pouruan.web.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SystemStatusAction {
	@RequestMapping(value = "/Admin/System/systemStatus.do", method = RequestMethod.GET)
	public String systemStatus(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		try {
			Map statusMap=new HashMap();
            // System��Ϣ����jvm��ȡ
			List jvmList=new ArrayList();
            property(jvmList);
            statusMap.put("jvmList",jvmList);
            // cpu��Ϣ
            List cpuList=new ArrayList();
            cpu(cpuList);
            statusMap.put("cpuList",cpuList);
            // �ڴ���Ϣ
            List memoryList=new ArrayList();
            memory(memoryList);
            statusMap.put("memoryList",memoryList);
            // ����ϵͳ��Ϣ
            List osList=new ArrayList();
            os(osList);
            statusMap.put("osList",osList);
            // �û���Ϣ
            List userList=new ArrayList();
            who(userList);
            statusMap.put("userList",userList);
            // �ļ�ϵͳ��Ϣ
            List fileList=new ArrayList();
            file(fileList);
            statusMap.put("fileList",fileList);
            // ������Ϣ
            List netList=new ArrayList();
            net(netList);
            statusMap.put("netList",netList);
            // ��̫����Ϣ
            List ethernetList=new ArrayList();
            ethernet(ethernetList);
            statusMap.put("ethernetList",ethernetList);
            model.addAttribute("statusMap", statusMap);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		return "Admin/System/systemStatus";
	}
	
	
	 private static void property(List list) throws UnknownHostException {
	        Runtime r = Runtime.getRuntime();
	        Properties props = System.getProperties();
	        InetAddress addr;
	        addr = InetAddress.getLocalHost();
	        String ip = addr.getHostAddress();
	        Map<String, String> map = System.getenv();
	        String userName = map.get("USERNAME");// ��ȡ�û���
	        String computerName = map.get("COMPUTERNAME");// ��ȡ�������
	        String userDomain = map.get("USERDOMAIN");// ��ȡ���������
	        list.add("�û���:    " + userName);
	        list.add("�������:    " + computerName);
	        list.add("���������:    " + userDomain);
	        list.add("����ip��ַ:    " + ip);
	        list.add("����������:    " + addr.getHostName());
	        list.add("JVM����ʹ�õ����ڴ�:    " + r.totalMemory());
	        list.add("JVM����ʹ�õ�ʣ���ڴ�:    " + r.freeMemory());
	        list.add("JVM����ʹ�õĴ���������:    " + r.availableProcessors());
	        list.add("Java�����л����汾��    " + props.getProperty("java.version"));
	        list.add("Java�����л�����Ӧ�̣�    " + props.getProperty("java.vendor"));
	        list.add("Java��Ӧ�̵�URL��    " + props.getProperty("java.vendor.url"));
	        list.add("Java�İ�װ·����    " + props.getProperty("java.home"));
	        list.add("Java��������淶�汾��   " + props.getProperty("java.vm.specification.version"));
	        list.add("Java��������淶��Ӧ�̣�  " + props.getProperty("java.vm.specification.vendor"));
	        list.add("Java��������淶���ƣ�  " + props.getProperty("java.vm.specification.name"));
	        list.add("Java�������ʵ�ְ汾��     " + props.getProperty("java.vm.version"));
	        list.add("Java�������ʵ�ֹ�Ӧ�̣�   " + props.getProperty("java.vm.vendor"));
	        list.add("Java�������ʵ�����ƣ�    " + props.getProperty("java.vm.name"));
	        list.add("Java����ʱ�����淶�汾��    " + props.getProperty("java.specification.version"));
	        list.add("Java����ʱ�����淶��Ӧ�̣�   " + props.getProperty("java.specification.vender"));
	        list.add("Java����ʱ�����淶���ƣ�   " + props.getProperty("java.specification.name"));
	        list.add("Java�����ʽ�汾�ţ�  " + props.getProperty("java.class.version"));
	        list.add("Java����·����    " + props.getProperty("java.class.path"));
	        list.add("���ؿ�ʱ������·���б�   " + props.getProperty("java.library.path"));
	        list.add("Ĭ�ϵ���ʱ�ļ�·����    " + props.getProperty("java.io.tmpdir"));
	        list.add("һ��������չĿ¼��·����   " + props.getProperty("java.ext.dirs"));
	        list.add("����ϵͳ�����ƣ�    " + props.getProperty("os.name"));
	        list.add("����ϵͳ�Ĺ��ܣ�    " + props.getProperty("os.arch"));
	        list.add("����ϵͳ�İ汾��    " + props.getProperty("os.version"));
	        list.add("�ļ��ָ�����    " + props.getProperty("file.separator"));
	        list.add("·���ָ�����    " + props.getProperty("path.separator"));
	        list.add("�зָ�����    " + props.getProperty("line.separator"));
	        list.add("�û����˻����ƣ�    " + props.getProperty("user.name"));
	        list.add("�û�����Ŀ¼��    " + props.getProperty("user.home"));
	        list.add("�û��ĵ�ǰ����Ŀ¼��    " + props.getProperty("user.dir"));
	    }

	    private static void memory(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        Mem mem = sigar.getMem();
	        // �ڴ�����
	        list.add("�ڴ�����:    " + mem.getTotal() / 1024L + "K av");
	        // ��ǰ�ڴ�ʹ����
	        list.add("��ǰ�ڴ�ʹ����:    " + mem.getUsed() / 1024L + "K used");
	        // ��ǰ�ڴ�ʣ����
	        list.add("��ǰ�ڴ�ʣ����:    " + mem.getFree() / 1024L + "K free");
	        Swap swap = sigar.getSwap();
	        // ����������
	        list.add("����������:    " + swap.getTotal() / 1024L + "K av");
	        // ��ǰ������ʹ����
	        list.add("��ǰ������ʹ����:    " + swap.getUsed() / 1024L + "K used");
	        // ��ǰ������ʣ����
	        list.add("��ǰ������ʣ����:    " + swap.getFree() / 1024L + "K free");
	    }

	    private static void cpu(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        CpuInfo infos[] = sigar.getCpuInfoList();
	        CpuPerc cpuList[] = null;
	        cpuList = sigar.getCpuPercList();
	        for (int i = 0; i < infos.length; i++) {// �����ǵ���CPU���Ƕ�CPU������
	            CpuInfo info = infos[i];
	            list.add("��" + (i + 1) + "��CPU��Ϣ");
	            list.add("CPU������MHz:    " + info.getMhz());// CPU������MHz
	            list.add("CPU������:  " + info.getVendor());// ���CPU���������磺Intel
	            list.add("CPU���:    " + info.getModel());// ���CPU������磺Celeron
	            list.add("CPU��������:    " + info.getCacheSize());// ����洢������
	            printCpuPerc(list,cpuList[i]);
	        }
	    }

	    private static void printCpuPerc(List list,CpuPerc cpu) {
	        list.add("CPU�û�ʹ����:    " + CpuPerc.format(cpu.getUser()));// �û�ʹ����
	        list.add("CPUϵͳʹ����:    " + CpuPerc.format(cpu.getSys()));// ϵͳʹ����
	        list.add("CPU��ǰ�ȴ���:    " + CpuPerc.format(cpu.getWait()));// ��ǰ�ȴ���
	        list.add("CPU��ǰ������:    " + CpuPerc.format(cpu.getNice()));//
	        list.add("CPU��ǰ������:    " + CpuPerc.format(cpu.getIdle()));// ��ǰ������
	        list.add("CPU�ܵ�ʹ����:    " + CpuPerc.format(cpu.getCombined()));
	        // �ܵ�ʹ����
	    }

	    private static void os(List list) {
	        OperatingSystem OS = OperatingSystem.getInstance();
	        // ����ϵͳ�ں������磺 386��486��586��x86
	        list.add("����ϵͳ:    " + OS.getArch());
	        list.add("����ϵͳCpuEndian():    " + OS.getCpuEndian());//
	        list.add("����ϵͳDataModel():    " + OS.getDataModel());//
	        // ϵͳ����
	        list.add("����ϵͳ������:    " + OS.getDescription());
	        // ����ϵͳ����
	        // list.add("OS.getName():    " + OS.getName());
	        // list.add("OS.getPatchLevel():    " + OS.getPatchLevel());//
	        // ����ϵͳ������
	        list.add("����ϵͳ������:    " + OS.getVendor());
	        // ��������
	        list.add("����ϵͳ��������:    " + OS.getVendorCodeName());
	        // ����ϵͳ����
	        list.add("����ϵͳ����:    " + OS.getVendorName());
	        // ����ϵͳ��������
	        list.add("����ϵͳ��������:    " + OS.getVendorVersion());
	        // ����ϵͳ�İ汾��
	        list.add("����ϵͳ�İ汾��:    " + OS.getVersion());
	    }

	    private static void who(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        Who who[] = sigar.getWhoList();
	        if (who != null && who.length > 0) {
	            for (int i = 0; i < who.length; i++) {
	                // list.add("��ǰϵͳ���̱��е��û���" + String.valueOf(i));
	                Who _who = who[i];
	                list.add("�û�����̨:    " + _who.getDevice());
	                list.add("�û�host:    " + _who.getHost());
	                // list.add("getTime():    " + _who.getTime());
	                // ��ǰϵͳ���̱��е��û���
	                list.add("��ǰϵͳ���̱��е��û���:    " + _who.getUser());
	            }
	        }
	    }

	    private static void file(List list) throws Exception {
	        Sigar sigar = new Sigar();
	        FileSystem fslist[] = sigar.getFileSystemList();
	        for (int i = 0; i < fslist.length; i++) {
	            list.add("�������̷�����" + i);
	            FileSystem fs = fslist[i];
	            // �������̷�����
	            list.add("�̷�����:    " + fs.getDevName());
	            // �������̷�����
	            list.add("�̷�·��:    " + fs.getDirName());
	            list.add("�̷���־:    " + fs.getFlags());//
	            // �ļ�ϵͳ���ͣ����� FAT32��NTFS
	            list.add("�̷�����:    " + fs.getSysTypeName());
	            // �ļ�ϵͳ�����������籾��Ӳ�̡������������ļ�ϵͳ��
	            list.add("�̷�������:    " + fs.getTypeName());
	            // �ļ�ϵͳ����
	            list.add("�̷��ļ�ϵͳ����:    " + fs.getType());
	            FileSystemUsage usage = null;
	            usage = sigar.getFileSystemUsage(fs.getDirName());
	            switch (fs.getType()) {
	            case 0: // TYPE_UNKNOWN ��δ֪
	                break;
	            case 1: // TYPE_NONE
	                break;
	            case 2: // TYPE_LOCAL_DISK : ����Ӳ��
	                // �ļ�ϵͳ�ܴ�С
	                list.add(fs.getDevName() + "�ܴ�С:    " + usage.getTotal() + "KB");
	                // �ļ�ϵͳʣ���С
	                list.add(fs.getDevName() + "ʣ���С:    " + usage.getFree() + "KB");
	                // �ļ�ϵͳ���ô�С
	                list.add(fs.getDevName() + "���ô�С:    " + usage.getAvail() + "KB");
	                // �ļ�ϵͳ�Ѿ�ʹ����
	                list.add(fs.getDevName() + "�Ѿ�ʹ����:    " + usage.getUsed() + "KB");
	                double usePercent = usage.getUsePercent() * 100D;
	                // �ļ�ϵͳ��Դ��������
	                list.add(fs.getDevName() + "��Դ��������:    " + usePercent + "%");
	                break;
	            case 3:// TYPE_NETWORK ������
	                break;
	            case 4:// TYPE_RAM_DISK ������
	                break;
	            case 5:// TYPE_CDROM ������
	                break;
	            case 6:// TYPE_SWAP ��ҳ�潻��
	                break;
	            }
	            list.add(fs.getDevName() + "������    " + usage.getDiskReads());
	            list.add(fs.getDevName() + "д�룺    " + usage.getDiskWrites());
	        }
	        return;
	    }

	    private static void net(List list) throws Exception {
	        Sigar sigar = new Sigar();
	        String ifNames[] = sigar.getNetInterfaceList();
	        for (int i = 0; i < ifNames.length; i++) {
	            String name = ifNames[i];
	            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
	            list.add("�����豸��:    " + name);// �����豸��
	            list.add("IP��ַ:    " + ifconfig.getAddress());// IP��ַ
	            list.add("��������:    " + ifconfig.getNetmask());// ��������
	            if ((ifconfig.getFlags() & 1L) <= 0L) {
	                list.add("!IFF_UP...skipping getNetInterfaceStat");
	                continue;
	            }
	            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
	            list.add(name + "���յ��ܰ�����:" + ifstat.getRxPackets());// ���յ��ܰ�����
	            list.add(name + "���͵��ܰ�����:" + ifstat.getTxPackets());// ���͵��ܰ�����
	            list.add(name + "���յ������ֽ���:" + ifstat.getRxBytes());// ���յ������ֽ���
	            list.add(name + "���͵����ֽ���:" + ifstat.getTxBytes());// ���͵����ֽ���
	            list.add(name + "���յ��Ĵ������:" + ifstat.getRxErrors());// ���յ��Ĵ������
	            list.add(name + "�������ݰ�ʱ�Ĵ�����:" + ifstat.getTxErrors());// �������ݰ�ʱ�Ĵ�����
	            list.add(name + "����ʱ�����İ���:" + ifstat.getRxDropped());// ����ʱ�����İ���
	            list.add(name + "����ʱ�����İ���:" + ifstat.getTxDropped());// ����ʱ�����İ���
	        }
	    }

	    private static void ethernet(List list) throws SigarException {
	        Sigar sigar = null;
	        sigar = new Sigar();
	        String[] ifaces = sigar.getNetInterfaceList();
	        for (int i = 0; i < ifaces.length; i++) {
	            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
	            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
	                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
	                continue;
	            }
	            list.add(cfg.getName() + "IP��ַ:" + cfg.getAddress());// IP��ַ
	            list.add(cfg.getName() + "���ع㲥��ַ:" + cfg.getBroadcast());// ���ع㲥��ַ
	            list.add(cfg.getName() + "����MAC��ַ:" + cfg.getHwaddr());// ����MAC��ַ
	            list.add(cfg.getName() + "��������:" + cfg.getNetmask());// ��������
	            list.add(cfg.getName() + "����������Ϣ:" + cfg.getDescription());// ����������Ϣ
	            list.add(cfg.getName() + "��������" + cfg.getType());//
	        }
	    }
}
