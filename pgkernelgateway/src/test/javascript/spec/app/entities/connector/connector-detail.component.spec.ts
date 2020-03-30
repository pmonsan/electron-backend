/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorDetailComponent } from 'app/entities/connector/connector-detail.component';
import { Connector } from 'app/shared/model/connector.model';

describe('Component Tests', () => {
  describe('Connector Management Detail Component', () => {
    let comp: ConnectorDetailComponent;
    let fixture: ComponentFixture<ConnectorDetailComponent>;
    const route = ({ data: of({ connector: new Connector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConnectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConnectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.connector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
