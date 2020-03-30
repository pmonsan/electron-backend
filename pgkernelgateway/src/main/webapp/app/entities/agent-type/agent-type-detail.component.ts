import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgentType } from 'app/shared/model/agent-type.model';

@Component({
  selector: 'jhi-agent-type-detail',
  templateUrl: './agent-type-detail.component.html'
})
export class AgentTypeDetailComponent implements OnInit {
  agentType: IAgentType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agentType }) => {
      this.agentType = agentType;
    });
  }

  previousState() {
    window.history.back();
  }
}
