export interface IConnector {
  id?: number;
  code?: string;
  label?: string;
  logic?: string;
  comment?: string;
  partnerCode?: string;
  meansofpaymentTypeCode?: string;
  active?: boolean;
  connectorTypeId?: number;
  pgModuleId?: number;
}

export class Connector implements IConnector {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public logic?: string,
    public comment?: string,
    public partnerCode?: string,
    public meansofpaymentTypeCode?: string,
    public active?: boolean,
    public connectorTypeId?: number,
    public pgModuleId?: number
  ) {
    this.active = this.active || false;
  }
}
