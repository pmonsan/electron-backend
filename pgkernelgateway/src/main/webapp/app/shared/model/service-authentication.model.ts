export interface IServiceAuthentication {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class ServiceAuthentication implements IServiceAuthentication {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
