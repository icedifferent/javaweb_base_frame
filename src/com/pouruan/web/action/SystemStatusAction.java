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
            // System信息，从jvm获取
			List jvmList=new ArrayList();
            property(jvmList);
            statusMap.put("jvmList",jvmList);
            // cpu信息
            List cpuList=new ArrayList();
            cpu(cpuList);
            statusMap.put("cpuList",cpuList);
            // 内存信息
            List memoryList=new ArrayList();
            memory(memoryList);
            statusMap.put("memoryList",memoryList);
            // 操作系统信息
            List osList=new ArrayList();
            os(osList);
            statusMap.put("osList",osList);
            // 用户信息
            List userList=new ArrayList();
            who(userList);
            statusMap.put("userList",userList);
            // 文件系统信息
            List fileList=new ArrayList();
            file(fileList);
            statusMap.put("fileList",fileList);
            // 网络信息
            List netList=new ArrayList();
            net(netList);
            statusMap.put("netList",netList);
            // 以太网信息
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
	        String userName = map.get("USERNAME");// 获取用户名
	        String computerName = map.get("COMPUTERNAME");// 获取计算机名
	        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
	        list.add("用户名:    " + userName);
	        list.add("计算机名:    " + computerName);
	        list.add("计算机域名:    " + userDomain);
	        list.add("本地ip地址:    " + ip);
	        list.add("本地主机名:    " + addr.getHostName());
	        list.add("JVM可以使用的总内存:    " + r.totalMemory());
	        list.add("JVM可以使用的剩余内存:    " + r.freeMemory());
	        list.add("JVM可以使用的处理器个数:    " + r.availableProcessors());
	        list.add("Java的运行环境版本：    " + props.getProperty("java.version"));
	        list.add("Java的运行环境供应商：    " + props.getProperty("java.vendor"));
	        list.add("Java供应商的URL：    " + props.getProperty("java.vendor.url"));
	        list.add("Java的安装路径：    " + props.getProperty("java.home"));
	        list.add("Java的虚拟机规范版本：   " + props.getProperty("java.vm.specification.version"));
	        list.add("Java的虚拟机规范供应商：  " + props.getProperty("java.vm.specification.vendor"));
	        list.add("Java的虚拟机规范名称：  " + props.getProperty("java.vm.specification.name"));
	        list.add("Java的虚拟机实现版本：     " + props.getProperty("java.vm.version"));
	        list.add("Java的虚拟机实现供应商：   " + props.getProperty("java.vm.vendor"));
	        list.add("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
	        list.add("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
	        list.add("Java运行时环境规范供应商：   " + props.getProperty("java.specification.vender"));
	        list.add("Java运行时环境规范名称：   " + props.getProperty("java.specification.name"));
	        list.add("Java的类格式版本号：  " + props.getProperty("java.class.version"));
	        list.add("Java的类路径：    " + props.getProperty("java.class.path"));
	        list.add("加载库时搜索的路径列表：   " + props.getProperty("java.library.path"));
	        list.add("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
	        list.add("一个或多个扩展目录的路径：   " + props.getProperty("java.ext.dirs"));
	        list.add("操作系统的名称：    " + props.getProperty("os.name"));
	        list.add("操作系统的构架：    " + props.getProperty("os.arch"));
	        list.add("操作系统的版本：    " + props.getProperty("os.version"));
	        list.add("文件分隔符：    " + props.getProperty("file.separator"));
	        list.add("路径分隔符：    " + props.getProperty("path.separator"));
	        list.add("行分隔符：    " + props.getProperty("line.separator"));
	        list.add("用户的账户名称：    " + props.getProperty("user.name"));
	        list.add("用户的主目录：    " + props.getProperty("user.home"));
	        list.add("用户的当前工作目录：    " + props.getProperty("user.dir"));
	    }

	    private static void memory(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        Mem mem = sigar.getMem();
	        // 内存总量
	        list.add("内存总量:    " + mem.getTotal() / 1024L + "K av");
	        // 当前内存使用量
	        list.add("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
	        // 当前内存剩余量
	        list.add("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
	        Swap swap = sigar.getSwap();
	        // 交换区总量
	        list.add("交换区总量:    " + swap.getTotal() / 1024L + "K av");
	        // 当前交换区使用量
	        list.add("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
	        // 当前交换区剩余量
	        list.add("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
	    }

	    private static void cpu(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        CpuInfo infos[] = sigar.getCpuInfoList();
	        CpuPerc cpuList[] = null;
	        cpuList = sigar.getCpuPercList();
	        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
	            CpuInfo info = infos[i];
	            list.add("第" + (i + 1) + "块CPU信息");
	            list.add("CPU的总量MHz:    " + info.getMhz());// CPU的总量MHz
	            list.add("CPU生产商:  " + info.getVendor());// 获得CPU的卖主，如：Intel
	            list.add("CPU类别:    " + info.getModel());// 获得CPU的类别，如：Celeron
	            list.add("CPU缓存数量:    " + info.getCacheSize());// 缓冲存储器数量
	            printCpuPerc(list,cpuList[i]);
	        }
	    }

	    private static void printCpuPerc(List list,CpuPerc cpu) {
	        list.add("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
	        list.add("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
	        list.add("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
	        list.add("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
	        list.add("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
	        list.add("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));
	        // 总的使用率
	    }

	    private static void os(List list) {
	        OperatingSystem OS = OperatingSystem.getInstance();
	        // 操作系统内核类型如： 386、486、586等x86
	        list.add("操作系统:    " + OS.getArch());
	        list.add("操作系统CpuEndian():    " + OS.getCpuEndian());//
	        list.add("操作系统DataModel():    " + OS.getDataModel());//
	        // 系统描述
	        list.add("操作系统的描述:    " + OS.getDescription());
	        // 操作系统类型
	        // list.add("OS.getName():    " + OS.getName());
	        // list.add("OS.getPatchLevel():    " + OS.getPatchLevel());//
	        // 操作系统的卖主
	        list.add("操作系统的卖主:    " + OS.getVendor());
	        // 卖主名称
	        list.add("操作系统的卖主名:    " + OS.getVendorCodeName());
	        // 操作系统名称
	        list.add("操作系统名称:    " + OS.getVendorName());
	        // 操作系统卖主类型
	        list.add("操作系统卖主类型:    " + OS.getVendorVersion());
	        // 操作系统的版本号
	        list.add("操作系统的版本号:    " + OS.getVersion());
	    }

	    private static void who(List list) throws SigarException {
	        Sigar sigar = new Sigar();
	        Who who[] = sigar.getWhoList();
	        if (who != null && who.length > 0) {
	            for (int i = 0; i < who.length; i++) {
	                // list.add("当前系统进程表中的用户名" + String.valueOf(i));
	                Who _who = who[i];
	                list.add("用户控制台:    " + _who.getDevice());
	                list.add("用户host:    " + _who.getHost());
	                // list.add("getTime():    " + _who.getTime());
	                // 当前系统进程表中的用户名
	                list.add("当前系统进程表中的用户名:    " + _who.getUser());
	            }
	        }
	    }

	    private static void file(List list) throws Exception {
	        Sigar sigar = new Sigar();
	        FileSystem fslist[] = sigar.getFileSystemList();
	        for (int i = 0; i < fslist.length; i++) {
	            list.add("分区的盘符名称" + i);
	            FileSystem fs = fslist[i];
	            // 分区的盘符名称
	            list.add("盘符名称:    " + fs.getDevName());
	            // 分区的盘符名称
	            list.add("盘符路径:    " + fs.getDirName());
	            list.add("盘符标志:    " + fs.getFlags());//
	            // 文件系统类型，比如 FAT32、NTFS
	            list.add("盘符类型:    " + fs.getSysTypeName());
	            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
	            list.add("盘符类型名:    " + fs.getTypeName());
	            // 文件系统类型
	            list.add("盘符文件系统类型:    " + fs.getType());
	            FileSystemUsage usage = null;
	            usage = sigar.getFileSystemUsage(fs.getDirName());
	            switch (fs.getType()) {
	            case 0: // TYPE_UNKNOWN ：未知
	                break;
	            case 1: // TYPE_NONE
	                break;
	            case 2: // TYPE_LOCAL_DISK : 本地硬盘
	                // 文件系统总大小
	                list.add(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
	                // 文件系统剩余大小
	                list.add(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
	                // 文件系统可用大小
	                list.add(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
	                // 文件系统已经使用量
	                list.add(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
	                double usePercent = usage.getUsePercent() * 100D;
	                // 文件系统资源的利用率
	                list.add(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
	                break;
	            case 3:// TYPE_NETWORK ：网络
	                break;
	            case 4:// TYPE_RAM_DISK ：闪存
	                break;
	            case 5:// TYPE_CDROM ：光驱
	                break;
	            case 6:// TYPE_SWAP ：页面交换
	                break;
	            }
	            list.add(fs.getDevName() + "读出：    " + usage.getDiskReads());
	            list.add(fs.getDevName() + "写入：    " + usage.getDiskWrites());
	        }
	        return;
	    }

	    private static void net(List list) throws Exception {
	        Sigar sigar = new Sigar();
	        String ifNames[] = sigar.getNetInterfaceList();
	        for (int i = 0; i < ifNames.length; i++) {
	            String name = ifNames[i];
	            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
	            list.add("网络设备名:    " + name);// 网络设备名
	            list.add("IP地址:    " + ifconfig.getAddress());// IP地址
	            list.add("子网掩码:    " + ifconfig.getNetmask());// 子网掩码
	            if ((ifconfig.getFlags() & 1L) <= 0L) {
	                list.add("!IFF_UP...skipping getNetInterfaceStat");
	                continue;
	            }
	            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
	            list.add(name + "接收的总包裹数:" + ifstat.getRxPackets());// 接收的总包裹数
	            list.add(name + "发送的总包裹数:" + ifstat.getTxPackets());// 发送的总包裹数
	            list.add(name + "接收到的总字节数:" + ifstat.getRxBytes());// 接收到的总字节数
	            list.add(name + "发送的总字节数:" + ifstat.getTxBytes());// 发送的总字节数
	            list.add(name + "接收到的错误包数:" + ifstat.getRxErrors());// 接收到的错误包数
	            list.add(name + "发送数据包时的错误数:" + ifstat.getTxErrors());// 发送数据包时的错误数
	            list.add(name + "接收时丢弃的包数:" + ifstat.getRxDropped());// 接收时丢弃的包数
	            list.add(name + "发送时丢弃的包数:" + ifstat.getTxDropped());// 发送时丢弃的包数
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
	            list.add(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
	            list.add(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
	            list.add(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
	            list.add(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
	            list.add(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
	            list.add(cfg.getName() + "网卡类型" + cfg.getType());//
	        }
	    }
}
