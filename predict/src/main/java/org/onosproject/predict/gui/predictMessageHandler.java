package org.onosproject.predict.gui;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;
import org.onosproject.cpman.gui.CpmanViewMessageHandler;
import org.onosproject.predict.predictService;
import org.onosproject.ui.RequestHandler;
import org.onosproject.ui.UiMessageHandler;
import org.onosproject.ui.chart.ChartModel;
import org.onosproject.ui.chart.ChartUtils;

import java.util.Collection;

public class predictMessageHandler extends UiMessageHandler {

    @Override
    protected Collection<RequestHandler> createRequestHandlers() {
        return ImmutableSet.of(
                new PredictRequestHandler()
        );
    }

    private final class PredictRequestHandler extends RequestHandler{


        private final String respType;
        public PredictRequestHandler() {
            super("predictRequest");
            this.respType = "predictRespond";
        }

        @Override
        public void process(ObjectNode payload) {
            predictService ps = get(predictService.class);

            ObjectNode rootNode = MAPPER.createObjectNode();
            rootNode.put("predict",ps.getpredict());
            rootNode.put("Real",ps.getReal());
            sendMessage(respType, rootNode);
        }
    }

}
