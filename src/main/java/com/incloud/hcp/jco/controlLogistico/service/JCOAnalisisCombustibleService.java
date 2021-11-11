package com.incloud.hcp.jco.controlLogistico.service;

import com.incloud.hcp.jco.controlLogistico.dto.*;

public interface JCOAnalisisCombustibleService {

    AnalisisCombusLisExports Listar(AnalisisCombusLisImports imports)throws Exception;
    ControlLogExports Detalle(AnalisisCombusImports imports)throws Exception;
    ControlDetalleExport Detalles(AnalisisCombusImports imports)throws Exception;
    QlikExport QlikView(QlikView imports)throws Exception;
    AnalisisDtoExport AnalisisCombu(AnalisisDtoImport imports) throws Exception;
}
