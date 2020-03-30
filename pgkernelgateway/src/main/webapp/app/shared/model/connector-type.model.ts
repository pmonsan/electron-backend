export interface IConnectorType {
  id?: number;
  label?: string;
  description?: string;
  active?: boolean;
}

export class ConnectorType implements IConnectorType {
  constructor(public id?: number, public label?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
