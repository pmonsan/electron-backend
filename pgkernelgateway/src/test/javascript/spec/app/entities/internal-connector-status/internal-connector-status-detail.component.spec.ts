/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorStatusDetailComponent } from 'app/entities/internal-connector-status/internal-connector-status-detail.component';
import { InternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

describe('Component Tests', () => {
  describe('InternalConnectorStatus Management Detail Component', () => {
    let comp: InternalConnectorStatusDetailComponent;
    let fixture: ComponentFixture<InternalConnectorStatusDetailComponent>;
    const route = ({ data: of({ internalConnectorStatus: new InternalConnectorStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InternalConnectorStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InternalConnectorStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.internalConnectorStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
