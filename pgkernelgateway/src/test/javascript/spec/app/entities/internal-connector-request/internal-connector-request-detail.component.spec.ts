/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorRequestDetailComponent } from 'app/entities/internal-connector-request/internal-connector-request-detail.component';
import { InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

describe('Component Tests', () => {
  describe('InternalConnectorRequest Management Detail Component', () => {
    let comp: InternalConnectorRequestDetailComponent;
    let fixture: ComponentFixture<InternalConnectorRequestDetailComponent>;
    const route = ({ data: of({ internalConnectorRequest: new InternalConnectorRequest(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorRequestDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InternalConnectorRequestDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternalConnectorRequestDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.internalConnectorRequest).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
