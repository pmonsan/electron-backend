import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';

@Component({
  selector: 'jhi-agent-delete-dialog',
  templateUrl: './agent-delete-dialog.component.html'
})
export class AgentDeleteDialogComponent {
  agent: IAgent;

  constructor(protected agentService: AgentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agentListModification',
        content: 'Deleted an agent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agent-delete-popup',
  template: ''
})
export class AgentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agent = agent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agent', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agent', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
