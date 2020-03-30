export interface IPg8583Callback {
  id?: number;
  partnerCode?: string;
  callbackUri?: string;
  httpMethod?: string;
  managerClass?: string;
  active?: boolean;
}

export class Pg8583Callback implements IPg8583Callback {
  constructor(
    public id?: number,
    public partnerCode?: string,
    public callbackUri?: string,
    public httpMethod?: string,
    public managerClass?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
