package com.github.ompc.greys.command;

import com.github.ompc.greys.advisor.Enhancer;
import com.github.ompc.greys.command.affect.EnhancerAffect;
import com.github.ompc.greys.command.affect.RowAffect;
import com.github.ompc.greys.command.annotation.Cmd;
import com.github.ompc.greys.server.Session;
import com.github.ompc.greys.util.Matcher;

import java.lang.instrument.Instrumentation;

/**
 * 关闭命令
 * Created by vlinux on 14/10/23.
 */
@Cmd(named = "shutdown", sort = 11, desc = "Shutdown the greys server, and exit the console.",
        eg = {
                "shutdown"
        })
public class ShutdownCommand implements Command {

    @Override
    public Action getAction() {
        return new RowAction() {
            @Override
            public RowAffect action(Session session, Instrumentation inst, Sender sender) throws Throwable {

                // 退出之前需要重置所有的增强类
                final EnhancerAffect enhancerAffect = Enhancer.reset(
                        inst,
                        new Matcher.WildcardMatcher("*")
                );

                sender.send(true, "Greys shutdown completed.\n");
                return new RowAffect(enhancerAffect.cCnt());
            }

        };
    }
}
