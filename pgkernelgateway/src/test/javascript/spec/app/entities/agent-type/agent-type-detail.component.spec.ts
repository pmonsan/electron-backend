/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AgentTypeDetailComponent } from 'app/entities/agent-type/agent-type-detail.component';
import { AgentType } from 'app/shared/model/agent-type.model';

describe('Component Tests', () => {
  describe('AgentType Management Detail Component', () => {
    let comp: AgentTypeDetailComponent;
    let fixture: ComponentFixture<AgentTypeDetailComponent>;
    const route = ({ data: of({ agentType: new AgentType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AgentTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgentTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgentTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agentType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
