export interface IInternalConnectorStatus {
  id?: number;
  status?: string;
  defaultReason?: string;
}

export class InternalConnectorStatus implements IInternalConnectorStatus {
  constructor(public id?: number, public status?: string, public defaultReason?: string) {}
}
