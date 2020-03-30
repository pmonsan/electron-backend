export interface IServiceIntegration {
  id?: number;
  customerRef?: string;
  serviceCode?: string;
  active?: boolean;
  partnerId?: number;
}

export class ServiceIntegration implements IServiceIntegration {
  constructor(
    public id?: number,
    public customerRef?: string,
    public serviceCode?: string,
    public active?: boolean,
    public partnerId?: number
  ) {
    this.active = this.active || false;
  }
}
