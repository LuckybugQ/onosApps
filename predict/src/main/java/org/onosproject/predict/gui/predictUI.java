package org.onosproject.predict.gui;

import com.google.common.collect.ImmutableList;
import org.onosproject.ui.UiExtension;
import org.onosproject.ui.UiExtensionService;
import org.onosproject.ui.UiMessageHandlerFactory;
import org.onosproject.ui.UiView;
import org.osgi.service.component.annotations.*;

import java.util.List;

import static org.onosproject.ui.UiView.Category.NETWORK;

@Component(immediate = true, enabled = true)
public class predictUI {
    private static final String CPMAN_ID = "predict";
    private static final String CPMAN_TEXT = "Predict";
    private static final String RES_PATH = "gui";
    private static final ClassLoader CL = predictUI.class.getClassLoader();

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected UiExtensionService uiExtensionService;

    // Factory for UI message handlers
    private final UiMessageHandlerFactory messageHandlerFactory =
            () -> ImmutableList.of(new predictMessageHandler());

    // List of application views
    private final List<UiView> views = ImmutableList.of(
            new UiView(NETWORK, CPMAN_ID, CPMAN_TEXT)
    );

    // Application UI extension
    private final UiExtension uiExtension =
            new UiExtension.Builder(CL, views)
                    .messageHandlerFactory(messageHandlerFactory)
                    .resourcePath(RES_PATH)
                    .build();

    @Activate
    protected void activate() {
        uiExtensionService.register(uiExtension);
    }

    @Deactivate
    protected void deactivate() {
        uiExtensionService.unregister(uiExtension);
    }
}
