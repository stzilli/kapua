/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.client.account;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.kapua.app.console.client.device.button.ConfigDiscardButton;
import org.eclipse.kapua.app.console.client.device.button.ConfigSaveButton;
import org.eclipse.kapua.app.console.client.messages.ConsoleMessages;
import org.eclipse.kapua.app.console.client.resources.icons.IconSet;
import org.eclipse.kapua.app.console.client.resources.icons.KapuaIcon;
import org.eclipse.kapua.app.console.client.ui.button.RefreshButton;
import org.eclipse.kapua.app.console.client.ui.label.Label;
import org.eclipse.kapua.app.console.client.util.FailureHandler;
import org.eclipse.kapua.app.console.client.util.KapuaLoadListener;
import org.eclipse.kapua.app.console.shared.model.GwtConfigComponent;
import org.eclipse.kapua.app.console.shared.model.GwtSession;
import org.eclipse.kapua.app.console.shared.model.GwtXSRFToken;
import org.eclipse.kapua.app.console.shared.model.account.GwtAccount;
import org.eclipse.kapua.app.console.shared.service.GwtAccountService;
import org.eclipse.kapua.app.console.shared.service.GwtAccountServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtCredentialService;
import org.eclipse.kapua.app.console.shared.service.GwtCredentialServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtDataService;
import org.eclipse.kapua.app.console.shared.service.GwtDataServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceService;
import org.eclipse.kapua.app.console.shared.service.GwtDeviceServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtGroupService;
import org.eclipse.kapua.app.console.shared.service.GwtGroupServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtRoleService;
import org.eclipse.kapua.app.console.shared.service.GwtRoleServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenService;
import org.eclipse.kapua.app.console.shared.service.GwtSecurityTokenServiceAsync;
import org.eclipse.kapua.app.console.shared.service.GwtUserService;
import org.eclipse.kapua.app.console.shared.service.GwtUserServiceAsync;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelStringProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanelSelectionModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AccountConfigComponents extends LayoutContainer {

    private static final ConsoleMessages MSGS = GWT.create(ConsoleMessages.class);

    private static final GwtSecurityTokenServiceAsync gwtXSRFService = GWT.create(GwtSecurityTokenService.class);
    private static final GwtAccountServiceAsync gwtAccountService = GWT.create(GwtAccountService.class);
    private static final GwtCredentialServiceAsync gwtCredentialService = GWT.create(GwtCredentialService.class);
    private static final GwtGroupServiceAsync gwtGroupService = GWT.create(GwtGroupService.class);
    private static final GwtRoleServiceAsync gwtRoleService = GWT.create(GwtRoleService.class);
    private static final GwtDataServiceAsync gwtDataService = GWT.create(GwtDataService.class);
    private static final GwtDeviceServiceAsync gwtDeviceService = GWT.create(GwtDeviceService.class);
    private static final GwtUserServiceAsync gwtUserService = GWT.create(GwtUserService.class);

    private GwtSession m_currentSession;

    private boolean m_dirty;
    private boolean m_initialized;
    private GwtAccount m_selectedAccount;
    private AccountTabConfiguration m_tabConfig;

    private ToolBar m_toolBar;

    private Button m_refreshButton;
    private boolean refreshProcess;

    private Button m_apply;
    private Button m_reset;

    private ContentPanel m_configPanel;
    private AccountConfigPanel m_devConfPanel;
    private BorderLayoutData m_centerData;

    @SuppressWarnings("rawtypes")
    private BaseTreeLoader m_loader;
    private TreeStore<ModelData> m_treeStore;
    private TreePanel<ModelData> m_tree;

    private boolean resetProcess;

    private boolean applyProcess;

    private final AsyncCallback<Void> applyConfigCallback = new AsyncCallback<Void>() {

        public void onFailure(Throwable caught) {
            FailureHandler.handle(caught);
            m_dirty = true;
            refresh();
        }

        public void onSuccess(Void arg0) {
            m_dirty = true;
            refresh();
        }
    };

    AccountConfigComponents(GwtSession currentSession,
            AccountTabConfiguration tabConfig) {
        m_currentSession = currentSession;
        m_tabConfig = tabConfig;
        m_dirty = false;
        m_initialized = false;
    }

    public void setAccount(GwtAccount selectedAccount) {
        m_dirty = true;
        m_selectedAccount = selectedAccount;
    }

    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setLayout(new FitLayout());
        setBorders(false);

        // init components
        initToolBar();
        initConfigPanel();

        ContentPanel accountConfigurationPanel = new ContentPanel();
        accountConfigurationPanel.setBorders(false);
        accountConfigurationPanel.setBodyBorder(false);
        accountConfigurationPanel.setHeaderVisible(false);
        accountConfigurationPanel.setLayout(new FitLayout());
        accountConfigurationPanel.setScrollMode(Scroll.AUTO);
        accountConfigurationPanel.setTopComponent(m_toolBar);
        accountConfigurationPanel.add(m_configPanel);

        add(accountConfigurationPanel);
        m_initialized = true;
    }

    private void initToolBar() {
        m_toolBar = new ToolBar();
        m_toolBar.setBorders(false);

        //
        // Refresh Button
        m_refreshButton = new RefreshButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!refreshProcess) {
                    refreshProcess = true;
                    m_refreshButton.setEnabled(false);

                    m_dirty = true;
                    refresh();

                    m_refreshButton.setEnabled(true);
                    refreshProcess = false;
                }
            }
        });

        m_refreshButton.setEnabled(true);
        m_toolBar.add(m_refreshButton);
        m_toolBar.add(new SeparatorToolItem());

        m_apply = new ConfigSaveButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!applyProcess) {
                    applyProcess = true;
                    m_apply.setEnabled(false);

                    apply();

                    m_apply.setEnabled(true);
                    applyProcess = false;
                }
            }
        });

        m_reset = new ConfigDiscardButton(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!resetProcess) {
                    resetProcess = true;
                    m_reset.setEnabled(false);

                    reset();

                    m_reset.setEnabled(true);
                    resetProcess = false;
                }
            }
        });

        m_apply.setEnabled(false);
        m_reset.setEnabled(false);

        m_toolBar.add(m_apply);
        m_toolBar.add(new SeparatorToolItem());
        m_toolBar.add(m_reset);
    }

    @SuppressWarnings("unchecked")
    private void initConfigPanel() {
        m_configPanel = new ContentPanel();
        m_configPanel.setBorders(false);
        m_configPanel.setBodyBorder(false);
        m_configPanel.setHeaderVisible(false);
        m_configPanel.setStyleAttribute("background-color", "white");
        m_configPanel.setScrollMode(Scroll.AUTO);

        BorderLayout borderLayout = new BorderLayout();
        m_configPanel.setLayout(borderLayout);

        // center
        m_centerData = new BorderLayoutData(LayoutRegion.CENTER);
        m_centerData.setMargins(new Margins(0));

        // west
        BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200);
        westData.setSplit(true);
        westData.setCollapsible(true);
        westData.setMargins(new Margins(0, 5, 0, 0));

        // loader and store
        RpcProxy<List<GwtConfigComponent>> proxy = new RpcProxy<List<GwtConfigComponent>>() {

            @Override
            protected void load(Object loadConfig, AsyncCallback<List<GwtConfigComponent>> callback) {
                if (m_selectedAccount != null) {
                    m_tree.mask(MSGS.loading());
                    gwtAccountService.findServiceConfigurations(m_selectedAccount.getId(), callback);
                    m_dirty = false;
                }
            }
        };

        m_loader = new BaseTreeLoader<GwtConfigComponent>(proxy);
        m_loader.addLoadListener(new DataLoadListener());

        m_treeStore = new TreeStore<ModelData>(m_loader);

        m_tree = new TreePanel<ModelData>(m_treeStore);
        m_tree.setWidth(200);
        m_tree.setDisplayProperty("componentName");
        m_tree.setBorders(true);
        m_tree.setLabelProvider(modelStringProvider);
        m_tree.setAutoSelect(true);
        m_tree.setStyleAttribute("background-color", "white");

        m_configPanel.add(m_tree, westData);

        //
        // Selection Listener for the component
        // make sure the form is not dirty before switching.
        m_tree.getSelectionModel().addListener(Events.BeforeSelect, new Listener<BaseEvent>() {

            @SuppressWarnings("rawtypes")
            @Override
            public void handleEvent(BaseEvent be) {

                SelectionEvent<ModelData> se = (SelectionEvent<ModelData>) be;

                final GwtConfigComponent componentToSwitchTo = (GwtConfigComponent) se.getModel();
                if (m_devConfPanel != null && m_devConfPanel.isDirty()) {

                    // cancel the event first
                    be.setCancelled(true);

                    // need to reselect the current entry
                    // as the BeforeSelect event cleared it
                    // we need to do this without raising events
                    TreePanelSelectionModel selectionModel = m_tree.getSelectionModel();
                    selectionModel.setFiresEvents(false);
                    selectionModel.select(false, m_devConfPanel.getConfiguration());
                    selectionModel.setFiresEvents(true);

                    // ask for confirmation before switching
                    MessageBox.confirm(MSGS.confirm(),
                            MSGS.deviceConfigDirty(),
                            new Listener<MessageBoxEvent>() {

                                public void handleEvent(MessageBoxEvent ce) {
                                    // if confirmed, delete
                                    Dialog dialog = ce.getDialog();
                                    if (dialog.yesText.equals(ce.getButtonClicked().getText())) {
                                        m_devConfPanel.removeFromParent();
                                        m_devConfPanel = null;
                                        m_tree.getSelectionModel().select(false, componentToSwitchTo);
                                    }
                                }
                            });
                } else {
                    refreshConfigPanel(componentToSwitchTo);

                    // this is needed to select the item in the Tree
                    // Temporarly disable the firing of the selection events
                    // to avoid an infinite loop as BeforeSelect would be invoked again.
                    TreePanelSelectionModel selectionModel = m_tree.getSelectionModel();
                    selectionModel.setFiresEvents(false);
                    selectionModel.select(false, componentToSwitchTo);

                    // renable firing of the events
                    selectionModel.setFiresEvents(true);
                }
            }
        });
    }

    public void refresh() {
        if (m_dirty && m_initialized) {

            // clear the tree and disable the toolbar
            m_apply.setEnabled(false);
            m_reset.setEnabled(false);
            m_refreshButton.setEnabled(false);

            m_treeStore.removeAll();

            // clear the panel
            if (m_devConfPanel != null) {
                m_devConfPanel.removeAll();
                m_devConfPanel.removeFromParent();
                m_devConfPanel = null;
                m_configPanel.layout();
            }

            m_loader.load();
        }
    }

    private void refreshConfigPanel(GwtConfigComponent configComponent) {
        m_apply.setEnabled(false);
        m_reset.setEnabled(false);

        if (m_devConfPanel != null) {
            m_devConfPanel.removeFromParent();
        }
        if (configComponent != null) {

            m_devConfPanel = new AccountConfigPanel(configComponent, m_currentSession, m_tabConfig.getSelectedEntity());
            m_devConfPanel.addListener(Events.Change, new Listener<BaseEvent>() {

                @Override
                public void handleEvent(BaseEvent be) {
                    m_apply.setEnabled(true);
                    m_reset.setEnabled(true);
                }
            });
            m_configPanel.add(m_devConfPanel, m_centerData);
            m_configPanel.layout();
        }
    }

    private void apply() {
        if (!m_devConfPanel.isValid()) {
            MessageBox mb = new MessageBox();
            mb.setIcon(MessageBox.ERROR);
            mb.setMessage(MSGS.deviceConfigError());
            mb.show();
            return;
        }

        // ask for confirmation
        String componentName = m_devConfPanel.getConfiguration().getComponentName();
        String message = MSGS.deviceConfigConfirmation(componentName);

        MessageBox.confirm(MSGS.confirm(),
                message,
                new Listener<MessageBoxEvent>() {

                    public void handleEvent(MessageBoxEvent ce) {

                        // if confirmed, push the update
                        // if confirmed, delete
                        Dialog dialog = ce.getDialog();
                        if (dialog.yesText.equals(ce.getButtonClicked().getText())) {

                            // mark the whole config panel dirty and for reload
                            m_tabConfig.setEntity(m_selectedAccount);

                            m_devConfPanel.mask(MSGS.applying());
                            m_tree.mask();
                            m_apply.setEnabled(false);
                            m_reset.setEnabled(false);
                            m_refreshButton.setEnabled(false);

                            //
                            // Getting XSRF token
                            gwtXSRFService.generateSecurityToken(new AsyncCallback<GwtXSRFToken>() {

                                @Override
                                public void onFailure(Throwable ex) {
                                    FailureHandler.handle(ex);
                                }

                                @Override
                                public void onSuccess(GwtXSRFToken token) {
                                    final GwtConfigComponent configComponent = m_devConfPanel.getUpdatedConfiguration();

                                    if ("AccountService".equals(configComponent.getComponentName())) {
                                        gwtAccountService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("CredentialService".equals(configComponent.getComponentName())) {
                                        gwtCredentialService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent,
                                                applyConfigCallback);
                                    } else if ("DeviceRegistryService".equals(configComponent.getComponentName())) {
                                        gwtDeviceService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("GroupService".equals(configComponent.getComponentName())) {
                                        gwtGroupService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("RoleService".equals(configComponent.getComponentName())) {
                                        gwtRoleService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("MessageStoreService".equals(configComponent.getComponentName())) {
                                        gwtDataService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    } else if ("UserService".equals(configComponent.getComponentName())) {
                                        gwtUserService.updateComponentConfiguration(token, m_selectedAccount.getId(), m_selectedAccount.getParentAccountId(), configComponent, applyConfigCallback);
                                    }
                                }
                            });

                            // start the configuration update
                        }
                    }
                });
    }

    public void reset() {
        final GwtConfigComponent comp = (GwtConfigComponent) m_tree.getSelectionModel().getSelectedItem();
        if (m_devConfPanel != null && comp != null && m_devConfPanel.isDirty()) {
            MessageBox.confirm(MSGS.confirm(),
                    MSGS.deviceConfigDirty(),
                    new Listener<MessageBoxEvent>() {

                        public void handleEvent(MessageBoxEvent ce) {
                            // if confirmed, delete
                            Dialog dialog = ce.getDialog();
                            if (dialog.yesText.equals(ce.getButtonClicked().getText())) {
                                refreshConfigPanel(comp);
                            }
                        }
                    });
        }
    }

    private ModelStringProvider<ModelData> modelStringProvider = new ModelStringProvider<ModelData>() {

        @Override
        public String getStringValue(ModelData model, String property) {

            KapuaIcon kapuaIcon = null;
            if (model instanceof GwtConfigComponent) {
                String iconName = ((GwtConfigComponent) model).getComponentIcon();

                if (iconName != null) {
                    if (iconName.startsWith("BluetoothService")) {
                        kapuaIcon = new KapuaIcon(IconSet.BTC);
                    } else if (iconName.startsWith("CloudService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CLOUD);
                    } else if (iconName.startsWith("DiagnosticsService")) {
                        kapuaIcon = new KapuaIcon(IconSet.AMBULANCE);
                    } else if (iconName.startsWith("ClockService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CLOCK_O);
                    } else if (iconName.startsWith("DataService")) {
                        kapuaIcon = new KapuaIcon(IconSet.DATABASE);
                    } else if (iconName.startsWith("MqttDataTransport")) {
                        kapuaIcon = new KapuaIcon(IconSet.FORUMBEE);
                    } else if (iconName.startsWith("PositionService")) {
                        kapuaIcon = new KapuaIcon(IconSet.LOCATION_ARROW);
                    } else if (iconName.startsWith("WatchdogService")) {
                        kapuaIcon = new KapuaIcon(IconSet.HEARTBEAT);
                    } else if (iconName.startsWith("SslManagerService")) {
                        kapuaIcon = new KapuaIcon(IconSet.LOCK);
                    } else if (iconName.startsWith("VpnService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CONNECTDEVELOP);
                    } else if (iconName.startsWith("ProvisioningService")) {
                        kapuaIcon = new KapuaIcon(IconSet.EXCLAMATION_CIRCLE);
                    } else if (iconName.startsWith("CommandPasswordService")) {
                        kapuaIcon = new KapuaIcon(IconSet.CHAIN);
                    } else if (iconName.startsWith("WebConsole")) {
                        kapuaIcon = new KapuaIcon(IconSet.LAPTOP);
                    } else if (iconName.startsWith("CommandService")) {
                        kapuaIcon = new KapuaIcon(IconSet.TERMINAL);
                    } else if (iconName.startsWith("DenaliService")) {
                        kapuaIcon = new KapuaIcon(IconSet.SPINNER);
                    } else {
                        kapuaIcon = new KapuaIcon(IconSet.PUZZLE_PIECE);
                    }
                }
            }

            Label label = new Label(((GwtConfigComponent) model).getComponentName(), kapuaIcon);

            return label.getText();
        }
    };

    // --------------------------------------------------------------------------------------
    //
    // Data Load Listener
    //
    // --------------------------------------------------------------------------------------

    private class DataLoadListener extends KapuaLoadListener {

        DataLoadListener() {
        }

        public void loaderLoad(LoadEvent le) {
            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }
            m_tree.unmask();
            m_refreshButton.setEnabled(true);
        }

        public void loaderLoadException(LoadEvent le) {

            if (le.exception != null) {
                FailureHandler.handle(le.exception);
            }

            List<ModelData> comps = new ArrayList<ModelData>();
            GwtConfigComponent comp = new GwtConfigComponent();
            comp.setId(MSGS.deviceNoDeviceSelected());
            comp.setName(MSGS.deviceNoComponents());
            comp.setDescription(MSGS.deviceNoConfigSupported());
            comps.add(comp);
            m_treeStore.removeAll();
            m_treeStore.add(comps, false);

            m_tree.unmask();
        }
    }
}
