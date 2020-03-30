export interface IAgentType {
  id?: number;
  label?: string;
  active?: boolean;
}

export class AgentType implements IAgentType {
  constructor(public id?: number, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
