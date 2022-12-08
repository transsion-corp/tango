package com.transsion.framework.tango.core.dag;

import com.transsion.framework.tango.common.exception.UnimplementedException;
import com.transsion.framework.tango.core.data.Data;
import com.transsion.framework.tango.core.data.meta.DataMetaRegistry;
import com.transsion.framework.tango.core.handler.ExpressionHandler;
import com.transsion.framework.tango.core.handler.ExpressionHandlerFactory;
import com.transsion.framework.tango.core.storage.Query;
import com.transsion.framework.tango.core.storage.Storage;
import com.transsion.framework.tango.core.storage.StorageAdapter;
import com.transsion.framework.tango.core.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @Author mengqi.lv
 * @Date 2022/8/31
 * @Version 1.0
 **/
public class DefaultNodeGenerator implements NodeGenerator {

    private final StorageAdapter storageAdapter;
    private final ExpressionHandlerFactory handlerFactory;
    private final Storage storage;
    private final DataMetaRegistry registry;
    private final ExecutorService executorService;

    public DefaultNodeGenerator(Storage storage, StorageAdapter storageAdapter, DataMetaRegistry metaRegistry,
                                ExpressionHandlerFactory handlerFactory, ExecutorService executorService) {
        this.storage = storage;
        this.storageAdapter = storageAdapter;
        this.registry = metaRegistry;
        this.handlerFactory = handlerFactory;
        this.executorService = executorService;
    }

    @Override
    public QueryNode genNode(View view) {
        // gen query by storage
        if (view.getDataId() != null) {
            if (!registry.hasMeta(view.getDataId())) {
                // TODO log error.
                throw new UnimplementedException("Data meta not found.");
            }
            Query query = storageAdapter.queryBuilder().setView(view).build();
            return new AbstructQueryNode() {
                Future<Data> future = executorService.submit(() -> storageAdapter.convertFrom(query, storage.query(query)));

                @Override
                public View getView() {
                    return view;
                }

                @Override
                public Data getData() {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        // TODO log error.
                        return Data.Empty;
                    }
                }

                @Override
                public void setParent(QueryNode pn) {
                    // TODO sub query.
                }
            };
        }
        return new AbstructQueryNode() {
            private final ExpressionHandler handler = handlerFactory.createHandler(view.getExpression());

            @Override
            public View getView() {
                return view;
            }

            @Override
            public Data getData() {
                return handler.handle();
            }

            @Override
            public void setParent(QueryNode pn) {
                handler.setSource(pn.getView().getViewId(), pn.getData());
            }
        };
    }
}
