import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgentType } from 'app/shared/model/agent-type.model';
import { AgentTypeService } from './agent-type.service';

@Component({
  selector: 'jhi-agent-type-delete-dialog',
  templateUrl: './agent-type-delete-dialog.component.html'
})
export class AgentTypeDeleteDialogComponent {
  agentType: IAgentType;

  constructor(protected agentTypeService: AgentTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agentTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agentTypeListModification',
        content: 'Deleted an agentType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agent-type-delete-popup',
  template: ''
})
export class AgentTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agentType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgentTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agentType = agentType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agent-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agent-type', { outlets: { popup: null } }]);
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
