export interface IServiceChannel {
  id?: number;
  channelCode?: string;
  active?: boolean;
  pgServiceId?: number;
}

export class ServiceChannel implements IServiceChannel {
  constructor(public id?: number, public channelCode?: string, public active?: boolean, public pgServiceId?: number) {
    this.active = this.active || false;
  }
}
